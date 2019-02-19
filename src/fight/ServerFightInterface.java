package fight;

import java.rmi.Remote;
import java.rmi.RemoteException;

import maze.People;

public interface ServerFightInterface extends Remote {
	/**
	 * Initialize the fight
	 * @param p1 : people one
	 * @param p2 : people tow
	 * @throws RemoteException
	 */
	void initializeFight(People p1, People p2) throws RemoteException;
	
	/**
	 * one fight turn
	 * @return People who take damage
	 * @throws RemoteException
	 */
	People turn() throws RemoteException;
}
