package fight;

import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ServerFight {
	public static void main(String args[]) throws RemoteException, AlreadyBoundException {

		try {

			Registry reg = null;
			reg = LocateRegistry.getRegistry(1099); // On recup registry du jeu pour crée new service dessus

			Naming.rebind("FightServer", new ServerFightImpl());// remoteService 
			System.err.println("Fight server ready");
		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace(); 
			System.out.println("Could not register RemoteService");
			System.exit(1);
		}

	}
}
