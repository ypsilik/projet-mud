package main;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote{
	
	public void createUser(String username) throws RemoteException;

	public String game(String nomCli, String direction) throws RemoteException;

}
