package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Maze {
	private int height;
	private int width;
	private int[][] maze;
	private static final int START_X = 0;
	private static final int START_Y = 1;
	private static final int OUT_X = 4;
	private static final int OUT_Y = 3;
	private HashMap<Player, Room> availablePlayer;
	private ArrayList<Room> rooms;

	/**
	 * Constructor Maze class
	 */
	public Maze() {
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
		this.availablePlayer = new HashMap<Player, Room>();
		this.rooms = new ArrayList<Room>();
	}

	/**
	 * Return all neighbour rooms from the current room
	 * @param currentRoomA : current player room
	 * @return tab int[4]
	 */
	public int[] getNeighbour(Room currentRoomA) {
		Room currentRoom = checkRoomExist(currentRoomA.getPosition().getX(), currentRoomA.getPosition().getY());
		int[] neighbourRooms = {2,2,2,2};
		if (currentRoom.getPosition().getX() != 0) {
			neighbourRooms[0] = this.maze[currentRoom.getPosition().getX()-1][currentRoom.getPosition().getY()]; // NORD
		}
		if (currentRoom.getPosition().getY() != this.width-1) {
			neighbourRooms[1] = this.maze[currentRoom.getPosition().getX()][currentRoom.getPosition().getY()+1]; // EST
		}
		if (currentRoom.getPosition().getX() != this.height-1) {
			neighbourRooms[2] = this.maze[currentRoom.getPosition().getX()+1][currentRoom.getPosition().getY()]; // SUD
		}
		if (currentRoom.getPosition().getY() != 0) {
			neighbourRooms[3] = this.maze[currentRoom.getPosition().getX()][currentRoom.getPosition().getY()-1]; // OUEST
		}
		return neighbourRooms;
	}


	/**
	 * Move player according to the direciton
	 * @param playerA
	 * @param direction
	 * @return new room
	 */
	public Room walkPlayer(Player playerA, Direction direction) {
		Player player = checkPlayerExist(playerA.getName());
		Room newPlayerRoom;
		switch (direction) {
		case N:
			newPlayerRoom = checkRoomExist(availablePlayer.get(player).getPosition().getX()-1, availablePlayer.get(player).getPosition().getY());
			movePlayer(player, newPlayerRoom);
			return newPlayerRoom;
		case E:
			newPlayerRoom = checkRoomExist(availablePlayer.get(player).getPosition().getX(), availablePlayer.get(player).getPosition().getY()+1);
			movePlayer(player, newPlayerRoom);
			return newPlayerRoom;
		case S:
			newPlayerRoom = checkRoomExist(availablePlayer.get(player).getPosition().getX()+1, availablePlayer.get(player).getPosition().getY());
			movePlayer(player, newPlayerRoom);
			return newPlayerRoom;
		case O:
			newPlayerRoom = checkRoomExist(availablePlayer.get(player).getPosition().getX(), availablePlayer.get(player).getPosition().getY()-1);
			movePlayer(player, newPlayerRoom);
			return newPlayerRoom;
		default:
			return null;
		}
	}

	/**
	 * Move player in new room
	 * @param playerA
	 * @param newPlayerRoomA
	 */
	private void movePlayer(Player playerA, Room newPlayerRoomA) {
		Player player = checkPlayerExist(playerA.getName());
		Room newPlayerRoom = checkRoomExist(newPlayerRoomA.getPosition().getX(), newPlayerRoomA.getPosition().getY());
		Room oldPiece = this.getPiece(availablePlayer.get(player));
		oldPiece.deadPlayer(); // delete the player from old room
		if (rooms.contains(newPlayerRoom)){
			Room currentRoom = getPiece(newPlayerRoom); // TODO pas un peu bizarre ? 
			availablePlayer.replace(player, currentRoom);
			currentRoom.newPlayer();
		} else {
			rooms.add(newPlayerRoom);
			availablePlayer.replace(player, newPlayerRoom);
			newPlayerRoom.newPlayer();
		}
	}

	/**
	 * Get the player current room 
	 * @param newPlayerRoom
	 * @return current room
	 */
	private Room getPiece(Room newPlayerRoom) {
		int roomIndex = rooms.indexOf(newPlayerRoom);
		Room currentRoom = rooms.get(roomIndex);
		return currentRoom;
	}


	/**
	 * Set player in room
	 * @param player
	 * @param room
	 */
	private void setPlayer(Player player, Room room) {
		rooms.add(room);
		availablePlayer.put(player, room);
	}

	/**
	 * get current player room
	 * @param playerA
	 * @return current room
	 */
	public Room getPlayerRoom(Player playerA) {
		Player player = this.checkPlayerExist(playerA.getName());
		Room room = availablePlayer.get(player); 
		return room;
	}

	/**
	 * Add new player in maze
	 * @param username
	 * @return new player object
	 */
	public Player addPlayer(String username) {
		Player player = this.checkPlayerExist(username);
		if (player == null) {
			player = new Player(username); 
		}
		this.setPlayer(player, new Room(new Position(START_X, START_Y))); // Init when the new user is created
		return player;
	}

	/**
	 * Check if is the end of maze
	 * @param room
	 * @return true if is the end, false if not
	 */
	public boolean isTheEnd(Room room){
		if (room.getPosition().getX() == OUT_X && room.getPosition().getY() == OUT_Y) {
			return true;
		}		
		return false;
	}

	/**
	 * check if player exist in the maze
	 * @param username
	 * @return player is exist, null if not
	 */
	public Player checkPlayerExist(String username) {
		if (availablePlayer.isEmpty()) {
			return null;
		} else {
			for (HashMap.Entry<Player, Room> entry : availablePlayer.entrySet()) { 
				Player key = entry.getKey();
				if (key.getName().equalsIgnoreCase(username)) {
					return key;
				}
			}
			return null;
		}
	}

	/**
	 * check if anyone have already pass a room
	 * @param x
	 * @param y
	 * @return new room or existing room
	 */
	public Room checkRoomExist(int x, int y) {
		if (availablePlayer.isEmpty()) {
			return new Room(new Position(x,y));
		} else {
			for (Room entry : rooms) {
				Room value = entry;
				if (value.getPosition().getX() == x && value.getPosition().getY() == y) {
					return value;
				}
			}
			return new Room(new Position(x,y));
		}
	}	

	/**
	 * List all player in the room
	 * @param room
	 * @return players array
	 */
	public ArrayList<Player> allPlayerInRoom(Room room) {
		ArrayList<Player> playersSameRoom = new ArrayList<Player>(); 
		for (HashMap.Entry<Player, Room> entry : availablePlayer.entrySet()) { 
			Player key = entry.getKey();
			Room value = entry.getValue();
			if ((value.getPosition().getX() == room.getPosition().getX()) && (value.getPosition().getY() == room.getPosition().getY())){
				playersSameRoom.add(key);
			}
		}
		return playersSameRoom;
	}

	public void updatePlayer(Player player) {
		//TODO
	}

	public ArrayList<Room> getRooms() {
		// TODO Auto-generated method stub
		return this.rooms;
	}

	public HashMap<Player, Room> getAvailablePlayer() {
		// TODO Auto-generated method stub
		return this.availablePlayer;
	}
	

}
