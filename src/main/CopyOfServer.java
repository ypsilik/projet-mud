/**
 * 
 */
package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import test.RMIimpl;
import maze.CopyOfMaze;
import maze.Direction;
import maze.Piece;
import maze.Player;
import maze.Position;

/**
 * @author Miki
 *
 */
public class CopyOfServer extends ServerImplementation{

	protected CopyOfServer() throws RemoteException {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			ServerImplementation srvImpl = new ServerImplementation();
			//ServerInterface srvInt = (ServerInterface) UnicastRemoteObject.exportObject(srvImpl, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			Naming.rebind("Game", srvImpl);  
			System.err.println("Server ready"); 
			
		} catch (Exception e) { 
			System.err.println("Server exception: " + e.toString()); 
			e.printStackTrace(); 
		} 
	}
	

}
