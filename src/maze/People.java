/**
 * 
 */
package maze;

import java.io.Serializable;

public abstract class People implements Serializable{
	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	protected int pv;


	public People(int pv) {
		this.pv = pv;
	}

	/**
	 * remove one pv of people 
	 */
	public void takeDamage() {
		this.pv --;
	}
	
	public int getPV() {
		return this.pv;
	}

	/**
	 * heal people 
	 */
	public abstract void heal(); // TODO : we heal monster ? 
}
