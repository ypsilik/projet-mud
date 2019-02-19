package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import maze.Room;
public interface ChatServerInterface extends Remote {
 
    void joinChatRoom(String string, Room r) throws RemoteException;  //  to join ChatClient
    void sendMessage (Room r, ChatClient c, String s) throws RemoteException; // to send message to any ChatClient
	ArrayList<String> getReply(Room roomPlayer) throws RemoteException;

}
