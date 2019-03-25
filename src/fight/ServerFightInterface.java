package fight;

import java.rmi.Remote;
import java.rmi.RemoteException;

import maze.Monster;
import maze.People;
import maze.Player;
import notif.NotifInterface;

public interface ServerFightInterface extends Remote {

	/**
	 * Initialize the fight
	 * @param p1 : people one
	 * @param p2 : people tow
	 * @throws RemoteException
	 */
	void initializeFight(Player p1, People p2, NotifInterface notifInt) throws RemoteException;

	/**
	 * one fight turn
	 * @return People who take damage
	 * @throws RemoteException
	 */
	People turn() throws RemoteException;
	void coNotif(Player player, NotifInterface notifInt)  throws RemoteException;
}
