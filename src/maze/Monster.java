/**
 * 
 */
package maze;

/**
 * @author Miki
 *
 */
public class Monster extends People{

	private static final int DEFAULT_PV = 5;

	public Monster(){
		super(DEFAULT_PV);
	}

	@Override
	public void heal() {
		this.pv = DEFAULT_PV;
	}
}
