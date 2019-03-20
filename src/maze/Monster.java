package maze;

import java.io.Serializable;

public class Monster implements Serializable{
	private static final int DEFAULT_PV = 5;
	private static int ID_GENE = 0;
	private int id;
	private int pv;
	public Monster(){
		this.id = ++this.ID_GENE;
		this.pv = DEFAULT_PV;
	}

	public void heal() {
		this.pv = DEFAULT_PV;
	}

	public int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Monster [id=" + id + ", pv=" + pv + "]";
	}

	public void takeDamage() {
		this.pv--;
	}

	public int getPV() {
		return this.pv;
	}
	
	public boolean equals(Object o){
		if( o  instanceof Monster){
			return this.id == (((Monster)o).id);
		}
		return false;
	}
}
