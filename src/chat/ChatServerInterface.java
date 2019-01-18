import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServerInterface extends Remote {
 
    void joinChatRoom(ChatClientInterface c) throws RemoteException;  //  to join ChatClient
    void leaveChatRoom(ChatClientInterface c) throws RemoteException;   //  to leave ChatClient from Chat Room
    void sendMessage (String s) throws RemoteException; // to send message to any ChatClient
    String[] getWhoisOnline() throws RemoteException;  // to get list of ChatClients who are joined in the  chat room. 

}
