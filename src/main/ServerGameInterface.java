package main;

import java.rmi.Remote;
import java.rmi.RemoteException;

import maze.Direction;
import maze.Room;
import maze.Player;

public interface ServerGameInterface extends Remote{

	/**
	 * Create new user in maze if not exist
	 * @param username
	 * @return new player
	 * @throws RemoteException
	 */
	public Player createUser(String username) throws RemoteException;

	/**
	 * See if player can move in the chosen direction
	 * @param player
	 * @param currentRoom
	 * @param direction
	 * @return true if he can, false if not
	 * @throws RemoteException
	 */
	public boolean canMove(Player player, Room currentRoom, String direction) throws RemoteException;

	/**
	 * Move player in the chosen direction
	 * @param player
	 * @param direction
	 * @return new player room
	 * @throws RemoteException
	 */
	public Room walkPlayer(Player player, Direction direction) throws RemoteException;

	/**
	 * display all neighbour rooms from the current room
	 * @param room
	 * @return String with rooms
	 * @throws RemoteException
	 */
	public String displayNeighbour(Room room) throws RemoteException;

	/**
	 * get current player room
	 * @param player
	 * @return player room
	 * @throws RemoteException
	 */
	public Room getRoom(Player player) throws RemoteException;

	/**
	 * check if new user room is the end of maze
	 * @param playerRoom
	 * @return true if is the end, false if not
	 * @throws RemoteException
	 */
	public boolean isTheEnd(Room playerRoom) throws RemoteException;
}
