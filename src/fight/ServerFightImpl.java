package fight;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import maze.Monster;
import maze.People;
import maze.Player;
import maze.Room;
import maze.ServerGameImplementation;
import notif.NotifInterface;

public class ServerFightImpl extends UnicastRemoteObject implements ServerFightInterface{

	private static final long serialVersionUID = 1L;
	private Player p1;
	private People p2;
	private ServerGameImplementation maze;
	private HashMap<Player, People> fightList;

	/**
	 * Constructor of ServerFightImplementation
	 * @throws RemoteException
	 */
	public ServerFightImpl(ServerGameImplementation maze) throws RemoteException {
		super();
		this.fightList = new HashMap<Player, People>();
		this.maze = maze;
	}

	/**
	 * Initialize the fight
	 * @param p1 : people one
	 * @param p2 : people two
	 * // IDEA : re-create People and made one function with (p2 instance of Player or Monster)
	 */
	public void initializeFight(Player p1, People p2, NotifInterface notifInt)  throws RemoteException {
		for (HashMap.Entry<Player, People> entry : fightList.entrySet()) { 
			Player player = entry.getKey();
			System.out.println("FIGHT INIT : " + fightList); // DEBUG
			if (player.getName().equals(p1.getName())) {
				System.out.println("FIGHT DEBUG : " + player); // DEBUG;
				this.p1 = player;
			}
		}
		if (p2 instanceof Monster) {
			this.p2 = (Monster) p2;
			System.out.println("FIGHT DEBUG 2: " + this.p1 + " --- " + this.p2); // DEBUG;
			fightList.replace(this.p1, this.p2);
		} else {
			this.p2 = (Player) p2;
			fightList.replace(this.p1, this.p2);
		}
	}

	/**
	 * one fight turn
	 * @return People who take damage
	 */
	public People turn() throws RemoteException{
		Random r = new Random();
		int min = 0;
		int max = 1;
		if(r.ints(min, (max + 1)).limit(1).findFirst().getAsInt() == 0) {
			p1.takeDamage();
		} else {
			if (p2.getPV() == 0) {
				notifyAllFighters();
				return p2;
			} else {
				p2.takeDamage();
				updateP2();
				notifyAllFighters();
				return p1;
			}
		}
		if (this.p2.getPV() != 0) {
			updateP2();	
		} else {
			notifyAllFighters();
			return p2;
		}
		notifyAllFighters();
		return p1;
	}

	private void updateP2() throws RemoteException {
		if (this.p2 instanceof Monster) {
			maze.getRoom(this.p1).updateMonster((Monster) this.p2);
			for (HashMap.Entry<Player, People> entry : fightList.entrySet()) { 
				Player key = entry.getKey();
				System.out.println("FIGHT UPDATE : " + entry.getValue()); // DEBUG
				if (((Monster) entry.getValue()).getId() == ((Monster) this.p2).getId()) {
					fightList.replace(key, this.p2);
				}
			}
		} else {
			maze.getRoom(p1).addPlayer((Player) p2);
			for (HashMap.Entry<Player, People> entry : fightList.entrySet()) { 
				Player key = entry.getKey();
				if (((Player) entry.getValue()).getName() == ((Player) p2).getName()) {
					fightList.replace(key, this.p2);
				}
			}
		}
		System.out.println("FIGHT LIST UPDATE" + fightList);
	}

	@Override
	public void coNotif(Player playerA, NotifInterface notifInt) {
		for (HashMap.Entry<Player, People> entry : fightList.entrySet()) { 
			Player player = entry.getKey();
			if (player.getName().equals(playerA.getName())) {
				player.setServerNotif(notifInt);
				return;
			}
		}
		fightList.put(playerA, null);
	}

	public void notifyAllFighters() {
		for (HashMap.Entry<Player, People> entry : fightList.entrySet()) { 
			Player key = entry.getKey();
			try {
				key.getServeurNotif().notify("You have " + key.getPV() + "pv. Monster have " + entry.getValue().getPV() + "pv. Run away ? [Y/N]");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

}
