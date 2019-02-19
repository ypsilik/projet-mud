package fight;

import java.util.Random;
import java.util.Scanner;

import maze.People;

public class Fight {
	private People p1;
	private People p2;

	/**
	 * Constructor of Fight
	 * @param p1 : people one
	 * @param p2 : people two
	 */
	public Fight(People p1, People p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	/**
	 * Start the fight turn
	 * @return People who take damage
	 */
	public People start() {
		Random r = new Random();
		int min = 0;
		int max = 1;
		if(r.ints(min, (max + 1)).limit(1).findFirst().getAsInt() == 0) {
			p1.takeDamage();
		} else {
			p2.takeDamage();
		}
		if (p2.getPV() == 0) {
			return null;
		} else {
			return p1;
		}
	}

}
