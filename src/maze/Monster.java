package maze;

import java.io.Serializable;

public class Monster extends People implements Serializable{
	private static final int DEFAULT_PV = 5;
	private static int ID_GENE = 0;
	private int id;
	
	public Monster(){
		super(DEFAULT_PV);
		this.id = ++this.ID_GENE;
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
	
	public boolean equals(Object o){
		if( o  instanceof Monster){
			return this.id == (((Monster)o).id);
		}
		return false;
	}
}
