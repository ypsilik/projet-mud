package maze;

import java.rmi.Remote;
import java.rmi.RemoteException;

import notif.NotifInterface;


public interface ServerGameInterface extends Remote{

	Player createUser(String chaine) throws RemoteException;

	Player getPlayer(String chaine) throws RemoteException;

	Player checkPlayerExists(String username) throws RemoteException;

	void coNotif(Player player, NotifInterface notifInt) throws RemoteException;

	Room getRoom(Player player) throws RemoteException;

	void displayMaze(Room room, Player player) throws RemoteException;

	void initPlayer(Player player) throws RemoteException;

	boolean walkPlayer(Player player, String chaine) throws RemoteException;

	void decoUser(Player player) throws RemoteException;

	void removeUser(Player player) throws RemoteException;

	void updatePlayer(Player player, People monster, Room room) throws RemoteException;

}
