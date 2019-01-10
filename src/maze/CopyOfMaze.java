/**
 * 
 */
package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Miki
 *
 */
public class CopyOfMaze {
	private int height;
	private int width;
	private int[][] maze;
	private HashMap<Player, Piece> dispoPlayer;
	private ArrayList<Piece> pieces;
	
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
		this.pieces = new ArrayList<Piece>();
	}
	
	public int[] getNeighbour(Piece posActu) {
		int[] neighbourPieces = {2,2,2,2};
		if (posActu.getPosition().getX() != 0) {
			neighbourPieces[0] = this.maze[posActu.getPosition().getX()-1][posActu.getPosition().getY()]; // NORD
		}
		if (posActu.getPosition().getY() != this.width-1) {
			neighbourPieces[1] = this.maze[posActu.getPosition().getX()][posActu.getPosition().getY()+1]; // EST
		}
		if (posActu.getPosition().getX() != this.height-1) {
			neighbourPieces[2] = this.maze[posActu.getPosition().getX()+1][posActu.getPosition().getY()]; // SUD
		}
		if (posActu.getPosition().getY() != 0) {
			neighbourPieces[3] = this.maze[posActu.getPosition().getX()][posActu.getPosition().getY()-1]; // OUEST
		}
		return neighbourPieces;
	}

	public void walkPlayer(Player player, Direction direction) {
		Piece newPlayerPiece;
		switch (direction) {
		case N:
			newPlayerPiece = checkPieceExists(dispoPlayer.get(player).getPosition().getX()-1, dispoPlayer.get(player).getPosition().getY());
			//newPlayerPiece = new Piece(new Position(dispoPlayer.get(player).getPosition().getX()-1, dispoPlayer.get(player).getPosition().getY()));
			movePlayer(player, newPlayerPiece);
			break;
		case E:
			newPlayerPiece = checkPieceExists(dispoPlayer.get(player).getPosition().getX(), dispoPlayer.get(player).getPosition().getY()+1);
//			newPlayerPiece = new Piece(new Position(dispoPlayer.get(player).getPosition().getX(), dispoPlayer.get(player).getPosition().getY()+1));
			movePlayer(player, newPlayerPiece);
			break;
		case S:
			newPlayerPiece = checkPieceExists(dispoPlayer.get(player).getPosition().getX()+1, dispoPlayer.get(player).getPosition().getY());
			//newPlayerPiece = new Piece(new Position(dispoPlayer.get(player).getPosition().getX()+1, dispoPlayer.get(player).getPosition().getY()));
			movePlayer(player, newPlayerPiece);
			break;
		case O:
			newPlayerPiece = checkPieceExists(dispoPlayer.get(player).getPosition().getX(), dispoPlayer.get(player).getPosition().getY()-1);
			//newPlayerPiece = new Piece(new Position(dispoPlayer.get(player).getPosition().getX(), dispoPlayer.get(player).getPosition().getY()-1));
			movePlayer(player, newPlayerPiece);
			break;
		default:
			break;
		}
	}

	private void movePlayer(Player player, Piece newPlayerPiece) {
		Piece oldPiece = this.getPiece(dispoPlayer.get(player));
		oldPiece.deadPlayer();
		if (pieces.contains(newPlayerPiece)){
			Piece pieceActu = getPiece(newPlayerPiece); 
			dispoPlayer.replace(player, pieceActu);
			pieceActu.newPlayer();
		} else {
			pieces.add(newPlayerPiece);
			dispoPlayer.replace(player, newPlayerPiece);
			newPlayerPiece.newPlayer();
		}
	}

	private Piece getPiece(Piece newPlayerPiece) {
		int indexPiece = pieces.indexOf(newPlayerPiece);
		Piece pieceActu = pieces.get(indexPiece);
		return pieceActu;
	}

	public void setPlayer(Player player, Piece piece) {
		pieces.add(piece);
		dispoPlayer.put(player, piece);
	}
	
	public Piece getPlayerPiece(Player player) {
		Piece piece = dispoPlayer.get(player); 
		return piece;
	}
	
	public void AllPlayerPiece() {
		ArrayList<Player> playersSamePiece = new ArrayList<Player>(); 
		Map<String, Integer> result = new TreeMap<String, Integer>();
		for (Map.Entry<Player, Piece> entry : dispoPlayer.entrySet()) { 
			Player key = entry.getKey();
			String value = entry.getValue().toString();
			Integer count = result.get(value);
			if (count == null) {
				result.put(value, new Integer(1));
			} else {
				result.put(value, new Integer(count+1));
			}
		}
		System.out.println(result.toString());
	}

	public Player getPlayer(String nomCli) {
		for (Map.Entry<Player, Piece> entry : dispoPlayer.entrySet()) { 
			Player key = entry.getKey();
			if (key.getName().equalsIgnoreCase(nomCli)) {
				return key;
			}
		}
		return null;
	}

	public void addPlayer(String username) {
		Player p = new Player(username);
		System.out.println("User created : " + username);
		Piece piece = checkPieceExists(0,1);
		this.setPlayer(p,piece);
		System.out.println(piece);
	}

	private Piece checkPieceExists(int x, int y) {
		for (Map.Entry<Player, Piece> entry : dispoPlayer.entrySet()) { 
			System.out.println("in foreach");
			Piece value = entry.getValue();
			if (value.getPosition().getX() != x && value.getPosition().getY() != y) {
				return new Piece(new Position(x,y));
			} else {
				return value;
			}
		}
		return null;
	}	
}
