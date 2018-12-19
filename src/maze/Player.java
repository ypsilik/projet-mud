/**
 * 
 */
package maze;

/**
 * @author Miki
 *
 */
public class Player extends People{
	private static final int DEFAULT_PV = 10;
	private String name;
	private int maxPv;
	
	public Player(String name) {
		super(DEFAULT_PV);
		this.name = name;
		this.maxPv = DEFAULT_PV;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void heal() {
		this.pv = maxPv;
	}
	
	public void lvlUp() {
		this.maxPv ++;
	}
}
