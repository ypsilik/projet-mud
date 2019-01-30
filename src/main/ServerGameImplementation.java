package main;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import maze.Maze;
import maze.Direction;
import maze.Room;
import maze.Player;

public class ServerGameImplementation extends UnicastRemoteObject implements ServerGameInterface {

	private static final long serialVersionUID = 1L;
	private Maze maze;

	/**
	 * Constructor ServerGameImplementation class
	 * @throws RemoteException
	 */
	protected ServerGameImplementation() throws RemoteException {
		super();
		maze = new Maze();
	}

	/* (non-Javadoc)
	 * @see main.ServerGameInterface#createUser(java.lang.String)
	 */
	public Player createUser(String username) throws RemoteException {
		Player player = maze.addPlayer(username);
		return player;
	}

	/* (non-Javadoc)
	 * @see main.ServerGameInterface#displayNeighbour(maze.Room)
	 */
	public String displayNeighbour(Room room) throws RemoteException { 
		int[] possibility = maze.getNeighbour(room);
		return "   " + possibility[0] + "\n" + possibility[3] + "     " + possibility[1] + "\n" + "   " + possibility[2];
	}

	/* (non-Javadoc)
	 * @see main.ServerGameInterface#canMove(maze.Player, maze.Room, java.lang.String)
	 */
	public boolean canMove(Player player, Room currentRoom, String direction) {
		Room roomStartTurn = currentRoom;
		int[] possibility = maze.getNeighbour(roomStartTurn);
		if ((direction.equalsIgnoreCase("N") && possibility[0] == 0) ||
				(direction.equalsIgnoreCase("E") && possibility[1] == 0) ||
				(direction.equalsIgnoreCase("S") && possibility[2] == 0) ||
				(direction.equalsIgnoreCase("O") && possibility[3] == 0)) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see main.ServerGameInterface#walkPlayer(maze.Player, maze.Direction)
	 */
	public Room walkPlayer(Player player, Direction direction) {
		return maze.walkPlayer(player, direction);
	}

	/* (non-Javadoc)
	 * @see main.ServerGameInterface#getRoom(maze.Player)
	 */
	public Room getRoom(Player player) throws RemoteException {
		return maze.getPlayerRoom(player);
	}

	/* (non-Javadoc)
	 * @see main.ServerGameInterface#isTheEnd(maze.Room)
	 */
	public boolean isTheEnd(Room playerRoom) throws RemoteException {
		return maze.isTheEnd(playerRoom);
	}
}
