package main;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import test.RMIint;


public class Client implements ClientInterface{
	private static ServerInterface srvInt;

	public static void main(String args[]){
		try {  
			// Getting the registry 
			Registry registry = LocateRegistry.getRegistry(1099);
			// Looking up the registry for the remote object 
			srvInt = (ServerInterface) Naming.lookup("Game"); 
			// System.out.println("Remote method invoked"); 
			System.err.println("Server connected");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString()); 
			e.printStackTrace(); 
		} 
		
		String chaine = "";
		String nomCli = "notset";
		Scanner scanner = new Scanner(System.in);
		System.out.print("Entrez votre nom d'utilisateur ou FIN pour arreter : ");

		while(!chaine.equalsIgnoreCase("FIN")) {
			chaine = scanner.nextLine();
//			System.out.println(chaine);
			if (chaine != null) {
				if (chaine.equalsIgnoreCase("FIN")) {
					
				}
				else if (nomCli.equalsIgnoreCase("notset")){
					try {
						srvInt.createUser(chaine);
						nomCli = chaine;
						System.out.println(srvInt.game(nomCli,""));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						System.out.println(srvInt.game(nomCli,chaine));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void messageFromServer(String message) throws RemoteException {
		System.out.println(message);
	}
}
