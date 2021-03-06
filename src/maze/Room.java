/**
 * 
 */
package maze;

import java.io.Serializable;

public class Room implements Serializable{
	private Position position;
	private int nbPlayer;
	private int nbMonster;

	/**
	 * Constructor room class
	 * @param position of room
	 */
	public Room(Position position) {
		this.position = position;
		this.nbPlayer = 0;
		this.nbMonster = 0;
	}

	/**
	 * Add player to room and pop new monster
	 */
	public void newPlayer() {
		this.nbMonster ++;
		this.nbPlayer ++;
	}


	/**
	 * Remove one player of room
	 */
	public void deadPlayer() {
		this.nbPlayer --;
	}

	/**
	 * Remove one monster of room
	 */
	public void deadMonster() {
		this.nbMonster --;
	}

	/**
	 * Get the room position
	 * @return room position
	 */
	public Position getPosition() {
		return new Position(this.position.getX(), this.position.getY());
	}

}
