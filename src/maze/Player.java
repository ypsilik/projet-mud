/**
 * 
 */
package maze;


public class Player extends People{
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_PV = 10;
	private String name;
	private int maxPv;

	public Player(String name) {
		super(DEFAULT_PV);
		this.name = name;
		this.maxPv = DEFAULT_PV;
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
		this.pv = maxPv;
	}

	/**
	 * Increase player pv by one
	 */
	public void lvlUp() {
		this.maxPv ++;
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
		return "Player [name=" + name + ", maxPv=" + maxPv + "]";
	}
	
	
}
