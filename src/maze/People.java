/**
 * 
 */
package maze;

/**
 * @author Miki
 *  abstraite ? 
 */
public abstract class People {
	protected int pv;
	
	
	public People(int pv) {
		this.pv = pv;
	}

	public void takeDamage() {
		this.pv --;
	}
	
	public abstract void heal();
}
