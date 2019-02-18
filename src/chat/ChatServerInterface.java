package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

import maze.Room;
public interface ChatServerInterface extends Remote {
 
    void joinChatRoom(String string, Room r) throws RemoteException;  //  to join ChatClient
    void leaveChatRoom(Room r) throws RemoteException;   //  to leave ChatClient from Chat Room
    void sendMessage (Room r, ChatClient c, String s) throws RemoteException; // to send message to any ChatClient

}
