package maze;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import notif.NotifInterface;

public class ServerGameImplementation  extends UnicastRemoteObject implements ServerGameInterface{

	private static final long serialVersionUID = 1L;

	private int height;
	private int width;
	private int[][] maze;
	private static final int START_X = 0;
	private static final int START_Y = 1;
	private static final int OUT_X = 4;
	private static final int OUT_Y = 3;

	private ArrayList<Room> rooms;
	private HashMap<String, Player> players;

	public ServerGameImplementation() throws RemoteException {
		super();
		this.height = 5;
		this.width = 5;
		int[][] maze = {
				{1,0,1,1,1}, // 0-0 / 0-1 / 0-2 ...
				{1,0,1,0,1}, // 1-0 / 1-1 / ...
				{1,0,0,0,1},
				{1,1,1,0,1},
				{1,1,1,0,1},
		};
		this.maze = maze;
		this.rooms = new ArrayList<Room>();
		this.players = new HashMap<String, Player>();

	}

	public Player createUser(String username) throws RemoteException {
		if (checkPlayerExists(username) == null) {
			players.put(username, new Player(username));
			return players.get(username);
		} else {
			return checkPlayerExists(username);
		}	
	}

	public Player getPlayer(String username) {
		System.out.println("GAME : " + players);
		return players.get(username);
	}



	public Player checkPlayerExists(String username) throws RemoteException {
		if (players.isEmpty()) {
			return null;
		} else {
			for (HashMap.Entry<String, Player> entry : players.entrySet()) { 
				String key = entry.getKey();
				if (key.equalsIgnoreCase(username)) {
					return entry.getValue();
				}
			}
			return null;
		}
	}

	public void coNotif(Player playerA, NotifInterface notifInt) throws RemoteException {
		Player player = this.players.get(playerA.getName());
		player.setServerNotif(notifInt);
	}

	public Room getRoom(Player player) throws RemoteException {
		for (Room room : rooms) {
			if(room.getPlayers().contains(player)) {
				return room;
			}
		}
		return null;
	}

	public void displayMaze(Room room, Player player) throws RemoteException {
		int[] neighbourRooms = {2,2,2,2};
		if (room.getPosition().getX() != 0) {
			neighbourRooms[0] = this.maze[room.getPosition().getX()-1][room.getPosition().getY()]; // NORD
		}
		if (room.getPosition().getY() != this.width-1) {
			neighbourRooms[1] = this.maze[room.getPosition().getX()][room.getPosition().getY()+1]; // EST
		}
		if (room.getPosition().getX() != this.height-1) {
			neighbourRooms[2] = this.maze[room.getPosition().getX()+1][room.getPosition().getY()]; // SUD
		}
		if (room.getPosition().getY() != 0) {
			neighbourRooms[3] = this.maze[room.getPosition().getX()][room.getPosition().getY()-1]; // OUEST
		}
		System.out.println("DISPLAY : " + room);
		players.get(player.getName()).getServeurNotif().notify("   " + neighbourRooms[0] + "\n" + neighbourRooms[3] + "     " + neighbourRooms[1] + "\n" + "   " + neighbourRooms[2]);
	}

	public void initPlayer(Player player) {
		Room room = checkRoomExist(START_X, START_Y);
		room.addPlayer(player);
	}

	private Room checkRoomExist(int x, int y) { // TODO : rename in createRoomIfNotExists ? 
		if (maze[x][y] != 1) {
			if (rooms.isEmpty()) {
				Room room = new Room(x,y);
				rooms.add(room);
				return room;
			} else {
				for (Room entry : rooms) {
					if (entry.getPosition().getX() == x && entry.getPosition().getY() == y) {
						return entry;
					}
				}
				Room room = new Room(x,y);
				rooms.add(room);
				return room;
			}
		} else {
			return null;
		}
	}

	public boolean walkPlayer(Player player, String direction) throws RemoteException {
		Room newPlayerRoom = null;
		switch (Direction.valueOf(direction)) {
		case N:
			newPlayerRoom = checkRoomExist(getRoom(player).getPosition().getX()-1, getRoom(player).getPosition().getY());
			break;
		case E:
			newPlayerRoom = checkRoomExist(getRoom(player).getPosition().getX(), getRoom(player).getPosition().getY()+1);
			break;
		case S:
			newPlayerRoom = checkRoomExist(getRoom(player).getPosition().getX()+1, getRoom(player).getPosition().getY());
			break;
		case O:
			newPlayerRoom = checkRoomExist(getRoom(player).getPosition().getX(), getRoom(player).getPosition().getY()-1);
			break;
		default :
			players.get(player).getServeurNotif().notify("It's a wall !");
			break;
		}
		if (newPlayerRoom == null) {
			players.get(player.getName()).getServeurNotif().notify("It's a wall !");
			return true;
		} else if (newPlayerRoom.getPosition().getX() == OUT_X && newPlayerRoom.getPosition().getY() == OUT_Y){
			players.get(player.getName()).getServeurNotif().notify("You see the light ! END");
			this.removeUser(player);
			return false;
		} else {
			rooms.set(rooms.indexOf(getRoom(player)), getRoom(player));
			getRoom(player).deletePlayer(player);
			newPlayerRoom.addMonster();
			newPlayerRoom.addPlayer(player);
			rooms.set(rooms.indexOf(newPlayerRoom),newPlayerRoom);
			System.out.println("MOVE : " + rooms);
			return true;
		}
	}

	@Override
	public void removeUser(Player player) {
		try {
			getRoom(player).deletePlayer(player);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		players.remove(player.getName());
	}

	
	@Override
	public void updatePlayer(Player player, People monster, Room room) throws RemoteException {
		players.put(player.getName(), player);
		room.addPlayer(player);
		if (monster instanceof Monster) {
			room.deleteMonster((Monster) monster);
		}
		rooms.set(rooms.indexOf(room),room);		
	}

	@Override
	public void decoUser(Player player) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
}
