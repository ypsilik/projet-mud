/**
 * 
 */
package maze;

/**
 * @author Miki
 *
 */
public class Piece {
	private Position position;
	private int nbPlayer;
	private int nbMonster;
	
	public Piece(Position position) {
		this.position = position;
		this.nbPlayer = 0;
		this.nbMonster = 0;
	}
	
	public void newPlayer() {
		this.nbMonster ++;
		this.nbPlayer ++;
	}
	
	public void deadPlayer() {
		this.nbPlayer ++;
	}
	
	public void deadMonster() {
		this.nbMonster ++;
	}

	public Position getPosition() {
		return new Position(this.position.getX(), this.position.getY());
	}

}
