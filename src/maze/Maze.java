/**
 * 
 */
package maze;

import java.util.HashMap;

/**
 * @author Miki
 *
 */
public class Maze {
	private int height;
	private int width;
	private int[][] maze;
	private HashMap<Player, Position> dispoPlayer;
	
	public Maze() {
		this.height = 5;
		this.width = 5;
		int[][] maze2 = {
				{1,0,1,1,1}, // 0-0 / 0-1 / 0-2 ...
				{1,0,1,0,1}, // 1-0 / 1-1 / ...
				{1,0,0,0,1},
				{1,1,1,0,1},
				{1,1,1,0,1},
		};
		this.maze = maze2;
		this.dispoPlayer = new HashMap<Player, Position>();
	}
	
	public int[] getNeighbour(Position posActu) {
		int[] neighbourPieces = {2,2,2,2};
		if (posActu.getX() != 0) {
			neighbourPieces[0] = this.maze[posActu.getX()-1][posActu.getY()]; // SUD
		}
		if (posActu.getY() != this.width) {
			neighbourPieces[1] = this.maze[posActu.getX()][posActu.getY()+1]; // EST
		}

		if (posActu.getX() != this.height); {
			neighbourPieces[2] = this.maze[posActu.getX()+1][posActu.getY()]; // NORD
		}
		if (posActu.getY() != 0) {
			neighbourPieces[3] = this.maze[posActu.getX()][posActu.getY()-1]; // OUEST
		}
		return neighbourPieces;
	}

	public void walkPlayer(Player p1, Position position) {
		if (maze[position.getX()][position.getY()] == 1) {
			System.out.println("It's a wall");
			
		} else {
			dispoPlayer.replace(p1, position);
		}
	}

	public void setPlayer(Player p1, Position position) {
		dispoPlayer.put(p1, position);
	}
	
	public Position getPlayerPosition(Player p1) {
		Position position = dispoPlayer.get(p1); 
		return position;
	}

}
