package maze;

import java.io.Serializable;

import notif.NotifImplementation;
import notif.NotifInterface;

public class Player extends People implements Serializable{

	private static int MAX_PV = 10;
	private String name;
	private int lvl;
	private NotifInterface notifInt;

	public Player(String name) {
		super(MAX_PV);
		this.name = name;
		this.lvl = 0;
	}
	
	public String getName() {
		return this.name;
	}
	public void heal() {
		this.pv = MAX_PV + this.lvl;
	}

	public void lvlUp(){
		this.lvl ++;
		this.heal();
	}

    public void setServerNotif(NotifInterface notifInterface) {
        this.notifInt = notifInterface;
    }

    public NotifInterface getServeurNotif() {
        return this.notifInt;
    }
    
    public boolean equals(Object o){
		if( o instanceof Player){
			return this.name.equals(((Player)o).name);
		}
		return false;
	}

    
	@Override
	public String toString() {
		return "Player [name=" + name + ", pv=" + pv + ", lvl=" + lvl + "]";
	}
}
