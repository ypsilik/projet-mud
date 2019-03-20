package fight;

import java.rmi.RemoteException;
import java.util.Random;
import java.util.Scanner;

import maze.Monster;
import maze.Player;

public class Fight {
	private Player p1;
	private Monster p2;
	private Player p3;


	/**
	 * Constructor of Fight
	 * @param p1 : people one
	 * @param p2 : people two
	 */
	public Fight(Player p1, Monster p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = null;
	}
	
	public Fight(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = null;
		this.p3 = p2;
	}

	/**
	 * Start the fight turn
	 * @return People who take damage
	 */
	public Player start() {
		Random r = new Random();
		int min = 0;
		int max = 1;
//		try {
//			p1.getServeurNotif().notify("You : " + p1.getPV());
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if(r.ints(min, (max + 1)).limit(1).findFirst().getAsInt() == 0) {
			p1.takeDamage();
		} else {
			if (p2 != null) {
				p2.takeDamage();
				if (p2.getPV() == 0) {
					return null;
				} else {
					return p1;
				}
			} else {
				p3.takeDamage();
				if (p3.getPV() == 0) {
					return null;
				} else {
					return p1;
				}
			}
		}
		return p1;
	}

}
