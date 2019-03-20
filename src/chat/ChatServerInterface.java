package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

import notif.NotifInterface;
import maze.Player;
import maze.Room;
public interface ChatServerInterface extends Remote {
 
    void sendMessage (Room r, Player p, String s) throws RemoteException; // to send message to any ChatClient

	void recordNotification(Player playerA, NotifInterface notif) throws RemoteException;


}
