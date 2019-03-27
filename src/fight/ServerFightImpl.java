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
			if (player.getName().equals(p1.getName())) {
				this.p1 = player;
			}
		}
		if (p2 instanceof Monster) {
			this.p2 = (Monster) p2;
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
			if (p1.getPV() == 0) {
				notifyAllFighters();
				return p1;
			} else {
				p1.takeDamage();
				updateCurrentFight();
				notifyAllFighters();
				return p1;
			}
		} else {
			if (p2.getPV() == 0) {
				notifyAllFighters();
				return p2;
			} else {
				p2.takeDamage();
				updateCurrentFight();
				notifyAllFighters();
				return p1;
			}
		}
	}

	private void updateCurrentFight() throws RemoteException {
		if (this.p2 instanceof Monster) {
			maze.getRoom(this.p1).updateMonster((Monster) this.p2);
			for (HashMap.Entry<Player, People> entry : fightList.entrySet()) { 
				Player key = entry.getKey();
				if (entry.getValue() != null && ((Monster) entry.getValue()).getId() == ((Monster) this.p2).getId()) {
					fightList.replace(key, this.p2);
				}
			}
		} else {
			maze.getRoom(this.p1).addPlayer((Player) this.p2);
			for (HashMap.Entry<Player, People> entry : fightList.entrySet()) { 
				Player key = entry.getKey();
				System.out.println("FIGHT GAME UPDATE P2 " + p2 + " Value " + entry.getValue() + " p1 " + entry.getKey()); // DEBUG
				if (entry.getValue() != null && ((Player) entry.getValue()).getName().equals(((Player) p2).getName())) {
					fightList.replace(key, this.p2);
					fightList.replace((Player) this.p2, key);
				}
			}
		}
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
		System.out.println("FIGHT : " + fightList); // DEBUG
		for (HashMap.Entry<Player, People> entry : fightList.entrySet()) { 
			Player key = entry.getKey();
			if (this.p2 instanceof Monster) {
				if (entry.getValue() != null && ((Monster) entry.getValue()).getId() == ((Monster) this.p2).getId()) {
					System.out.println("FIGHT BOUCLE : " + entry.getKey() + " -- " + entry.getValue()); // DEBUG
					if (this.p2.getPV() == 0) {
						try {
							key.getServeurNotif().notify("You have " + key.getPV() + "pv. Monster is dead - press enter");
							fightList.replace(key, null);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					} else {
						try {
							key.getServeurNotif().notify("You have " + key.getPV() + "pv. Monster have " + entry.getValue().getPV() + "pv. Run away ? [Y/N]");
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				if (entry.getValue() != null && ((Player) entry.getValue()).getName().equals(((Player) this.p2).getName())) {
					if (this.p2.getPV() == 0) {
						try {
							key.getServeurNotif().notify("You have " + key.getPV() + "pv. Player " + ((Player) entry.getValue()).getName() + " is dead - press enter");
							maze.notifyRival(key, (Player) entry.getValue(), "You are dead");
							fightList.replace(key, null);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					} else {
						try {
							key.getServeurNotif().notify("You have " + key.getPV() + "pv. Player " + ((Player) entry.getValue()).getName() + " have " + entry.getValue().getPV() + "pv. Run away ? [Y/N]");
							maze.notifyRival(key, (Player) entry.getValue(), "You have " + entry.getValue().getPV() + "pv. Player " + key.getName() + " have " + key.getPV() +"pv");
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

}
