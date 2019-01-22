package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClientInterface extends Remote {

	void getMessage(String s) throws RemoteException; //get message from server

}
