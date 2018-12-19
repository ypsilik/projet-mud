/**
 * 
 */
package main;

import maze.Maze;
import maze.Player;
import maze.Position;

/**
 * @author Miki
 *
 */
public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Maze maze = new Maze();
		Player p1 = new Player("toto");
		maze.setPlayer(p1,new Position(0,1));
		Position position = maze.getPlayerPosition(p1);
		int[] possibility = maze.getNeighbour(position);
		System.out.println("   " + possibility[0]);
		System.out.println(possibility[1] + "     " + possibility[3]);
		System.out.println("   " + possibility[2]);
		
		maze.walkPlayer(p1,new Position(1,1));
		Position position2 = maze.getPlayerPosition(p1);
		int[] possibility2 = maze.getNeighbour(position2);
		System.out.println("   " + possibility2[0]);
		System.out.println(possibility2[1] + "     " + possibility2[3]);
		System.out.println("   " + possibility2[2]);		
	}

}
