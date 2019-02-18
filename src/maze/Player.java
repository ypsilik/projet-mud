/**
 * 
 */
package maze;

import java.rmi.RemoteException;

public class Player extends People{
	private static final long serialVersionUID = 1L;
	private static int DEFAULT_PV = 10;
	private String name;
	private int maxPV;
	public Room room;

	public Player(String name) {
		super(DEFAULT_PV);
		this.maxPV = 10;
		this.name = name;
	}

	/**
	 * get player name
	 * @return String : player name
	 */
	public String getName() {
		return name;
	}

	/**
	 * set player name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void heal() {
		this.pv = maxPV;
	}

	/**
	 * Increase player pv by one
	 */
	public void lvlUp() {
		this.maxPV++;
		this.heal();

	}


	@Override
	public boolean equals(Object o){
		if( o  instanceof Player){
			return this.name  == ((Player)o).name;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Player [name=" + this.name + ", maxPv=" + this.maxPV + "]";
	}
	
	
}
