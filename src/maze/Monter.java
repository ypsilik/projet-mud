/**
 * 
 */
package maze;

/**
 * @author Miki
 *
 */
public class Monter extends People{

	private static final int DEFAULT_PV = 5;

	public Monter(){
		super(DEFAULT_PV);
	}

	@Override
	public void heal() {
		this.pv = DEFAULT_PV;
	}
}
