package fight;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import maze.Monster;
import maze.Player;
import notif.NotifInterface;

public class ServerFightImpl  extends UnicastRemoteObject implements ServerFightInterface{

	private static final long serialVersionUID = 1L;
	private Player p1;
	private Monster p2;
	private Player p3;

	/**
	 * Constructor of ServerFightImplementation
	 * @throws RemoteException
	 */
	public ServerFightImpl() throws RemoteException {
		super();
	}

	/**
	 * Initialize the fight
	 * @param p1 : people one
	 * @param p2 : people two
	 */
	public void initializeFight(Player p1, Monster p2)  throws RemoteException {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = null;
	}
	public void initializeFight(Player p1, Player p3)  throws RemoteException {
		this.p1 = p1;
		this.p2 = null;
		this.p3 = p3;
	}
	/**
	 * one fight turn
	 * @return People who take damage
	 */
	public Player turn() throws RemoteException{
		if (p2 == null) {
			return new Fight(p1, p3).start();
		} else {
			return new Fight(p1, p2).start();
		}
	}

	@Override
	public void coNotif(Player player, NotifInterface notifInt) {
		player.setServerNotif(notifInt);		
	}

}
