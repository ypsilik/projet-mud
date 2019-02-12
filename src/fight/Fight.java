package fight;

import java.util.Random;
import java.util.Scanner;

import maze.People;

public class Fight {
	private People p1;
	private People p2;

	public Fight(People p1, People p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public People start() {
		Random r = new Random();
		int min = 0;
		int max = 1;
//		Scanner in = new Scanner(System.in);
//		String msg;
//		while (true) {
//			System.out.println("fuir ?");
//			msg = in.nextLine();
//			if (msg.equals("Y")) {
//				break;
//			}else {
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
				//System.out.println(" P1 vie : " + p1.getPV() + " -- p2 vie : " + p2.getPV());
//			}
	}

}
