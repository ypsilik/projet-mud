package main;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote{
	public void messageFromServer(String message) throws RemoteException;
}
