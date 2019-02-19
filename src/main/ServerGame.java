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
import fight.ServerFightImpl;

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
			ServerGameImplementation gameSrvImpl = new ServerGameImplementation(); // Start game
			ChatServerImpl chatSrvImpl = new ChatServerImpl(gameSrvImpl.getMaze()); // Start chat
			ServerFightImpl fightSrvImpl = new ServerFightImpl(); // Start Fight

			Registry registry = LocateRegistry.createRegistry(1099); // Create registry
			Naming.rebind("Game", gameSrvImpl); // Expose game
			Naming.rebind("ChatServer", chatSrvImpl); // Expose chat
			Naming.rebind("FightServer", fightSrvImpl); // Expose fight
			System.err.println("Servers ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}


}
