/**
 * 
 */
package maze;

import java.util.HashMap;
import java.util.List;

/**
 * @author Miki
 *
 */
public class CopyOfMaze {
	private int height;
	private int width;
	private int[][] maze;
	private HashMap<Player, Piece> dispoPlayer;
	private Piece[] pieces;
	
	public CopyOfMaze() {
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
		this.dispoPlayer = new HashMap<Player, Piece>();
		
	}
	
	public int[] getNeighbour(Piece posActu) {
		int[] neighbourPieces = {2,2,2,2};
		if (posActu.getPosition().getX() != 0) {
			neighbourPieces[0] = this.maze[posActu.getPosition().getX()-1][posActu.getPosition().getY()]; // SUD
		}
		if (posActu.getPosition().getY() != this.width) {
			neighbourPieces[1] = this.maze[posActu.getPosition().getX()][posActu.getPosition().getY()+1]; // EST
		}

		if (posActu.getPosition().getX() != this.height); {
			neighbourPieces[2] = this.maze[posActu.getPosition().getX()+1][posActu.getPosition().getY()]; // NORD
		}
		if (posActu.getPosition().getY() != 0) {
			neighbourPieces[3] = this.maze[posActu.getPosition().getX()][posActu.getPosition().getY()-1]; // OUEST
		}
		return neighbourPieces;
	}

	public void walkPlayer(Player p1, Direction direction) {
		switch (direction) {
		case N:
			Piece newPlayerPiece = new Piece(new Position(dispoPlayer.get(p1).getPosition().getX()-1, dispoPlayer.get(p1).getPosition().getY()));
			if (pieces.contains(newPlayerPiece)) {
				Piece newPlayerPieceExist
				dispoPlayer.replace(p1,pieces.indexOf(newPlayerPiece)
			}
			dispoPlayer.replace(p1, new Piece(new Position(dispoPlayer.get(p1).getPosition().getX()-1, dispoPlayer.get(p1).getPosition().getY())));
			break;
		case E:
			dispoPlayer.replace(p1, new Piece(new Position(dispoPlayer.get(p1).getPosition().getX(), dispoPlayer.get(p1).getPosition().getY()+1)));
			break;
		case S:
			dispoPlayer.replace(p1, new Piece(new Position(dispoPlayer.get(p1).getPosition().getX()+1, dispoPlayer.get(p1).getPosition().getY())));
			break;
		case O:
			dispoPlayer.replace(p1, new Piece(new Position(dispoPlayer.get(p1).getPosition().getX()-1, dispoPlayer.get(p1).getPosition().getY()-1)));
			break;
		default:
			break;
		}
	}

	public void setPlayer(Player p1, Piece piece) {
		dispoPlayer.put(p1, piece);
	}
	
	public Piece getPlayerPiece(Player p1) {
		Piece piece = dispoPlayer.get(p1); 
		return piece;
	}

}
