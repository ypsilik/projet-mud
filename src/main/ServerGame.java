/**
 * 
 */
package main;

import java.nio.channels.ServerSocketChannel;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import chat.ChatServerImpl;

public class ServerGame extends ServerGameImplementation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ServerGame() throws RemoteException {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			ServerGameImplementation srvImpl = new ServerGameImplementation();
			ChatServerImpl chatSrvImpl = new ChatServerImpl(srvImpl.getMaze());
			
			Registry registry = LocateRegistry.createRegistry(1099);
			Naming.rebind("Game", srvImpl);
			Naming.rebind("ChatServer", chatSrvImpl);
			System.err.println("Game server ready"); 
		} catch (Exception e) { 
			System.err.println("Server exception: " + e.toString()); 
			e.printStackTrace(); 
		} 
	}


}