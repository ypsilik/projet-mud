package maze;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable{
	private static int ID_GENE = 0;
	private int id;
	private Position position;
	private ArrayList<Player> players;
	private ArrayList<Monster> monsters;


	/**
	 * Constructor room class
	 * @param position of room
	 */
	public Room(int x, int y) {
		this.id = ++this.ID_GENE;
		this.position = new Position(x, y);
		this.monsters = new ArrayList<Monster>();
		this.players = new ArrayList<Player>();
	}
	
	/**
	 * Add player to room and pop new monster
	 */
	public void addPlayer(Player player) {
		if (this.players.contains(player)) {
			this.players.set(this.players.indexOf(player), player);
		} else {
			this.players.add(player);
		}
	}


	/**
	 * Remove one player of room
	 */
	public void deletePlayer(Player player) {
		this.players.remove(player);
	}

	/**
	 * Remove one monster of room
	 * @param monster 
	 */
	public void deleteMonster(Monster monster) {
		if (this.monsters.contains(monster)) {
			this.monsters.remove(this.monsters.indexOf(monster));
		}
	}

	/**
	 * Get the room position
	 * @return room position
	 */
	public Position getPosition() {
		return new Position(this.position.getX(), this.position.getY());
	}

	public void addMonster() {
		Monster monster = new Monster();
		this.monsters.add(monster);
	}

	public ArrayList<Monster> getMonsters() {
		return this.monsters;
	}
	
	public ArrayList<Player> getPlayers(){
		return this.players;
	}

	public int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Room [id=" + id + ", position=" + position + ", monsters="
				+ monsters + ", players=" + players + "]";
	}

	public Player searchPlayer(String username) {
		for (Player player : players) {
			if (player.getName().equals(username)) {
				return player;
			}
		}
		return null;
	}

	public Monster searchMonster(int idMonster) {
		for (Monster monster : monsters) {
			if (monster.getId() == idMonster) {
				return monster;
			}
		}
		return null;
	}
	
	
	public boolean equals(Object o){
		if( o  instanceof Room){
			return this.id  == ((Room)o).id;
		}
		return false;
	}

	public void updateMonster(Monster monster) {
		if (this.monsters.contains(monster)) {
		this.monsters.set(this.monsters.indexOf(monster), monster);		
		}
	}
}
