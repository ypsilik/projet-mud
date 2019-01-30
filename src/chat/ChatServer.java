package chat;

import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {

	public static void main(String args[]) throws RemoteException, AlreadyBoundException {

		try {

			Registry reg = null;
			reg = LocateRegistry.createRegistry(1099);
			reg = LocateRegistry.getRegistry(1099);

			Naming.rebind("ChatServer", new ChatServerImpl()); // remoteService
			System.err.println("Chat server ready");
			System.out.println("Waiting for Request");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Could not register RemoteService");
			System.exit(1);
		}

	}

}
