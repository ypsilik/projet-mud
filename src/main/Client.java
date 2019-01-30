package main;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import chat.ChatClientImpl;
import chat.ChatServerInterface;
import maze.Direction;
import maze.Room;
import maze.Player;


public class Client{
	private static ServerGameInterface serverGameInt;
	private static Player player = null;
	private static Room roomPlayer = null;
	
	public static void main(String args[]){
		String urlGame = "rmi://localhost/Game";
		try {
			// Getting the registry
			Registry registryGame = LocateRegistry.getRegistry(1099);
			// Looking up the registry for the remote object
			serverGameInt = (ServerGameInterface) Naming.lookup(urlGame);

			System.err.println("Server connected");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}

		String chaine = "";
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter your name - \"END\" close connection : ");

		while(!chaine.equalsIgnoreCase("END")) {
			chaine = scanner.nextLine();
			if (chaine != null) {
				if (chaine.equalsIgnoreCase("END")) {
					System.out.println("Close client");
					System.exit(1);
				} else if (chaine.startsWith("\"")){
					System.out.println("chat");
				}
				else if (player == null){ // Start maze part
					try { // first connection
						player = serverGameInt.createUser(chaine);
						roomPlayer = serverGameInt.getRoom(player);
						System.out.println("You are " + player.getName());
						System.out.println(serverGameInt.displayNeighbour(roomPlayer));
						System.out.print("Where do you want to go ? [N/E/S/O] : ");
					} catch (RemoteException e) {
						e.printStackTrace();
						break;
					}
				} else if (chaine.equals("N") || chaine.equals("E") || chaine.equals("S") || chaine.equals("O")) {
					try {
						if (serverGameInt.canMove(player, roomPlayer, chaine)) {
							roomPlayer = serverGameInt.walkPlayer(player, Direction.valueOf(chaine));
							if (serverGameInt.isTheEnd(roomPlayer)) {
								System.out.println("You see the light ! END");
								break;
							}
							System.out.println(serverGameInt.displayNeighbour(roomPlayer));
							System.out.print("Where do you want to go ? [N/E/S/O] : ");
						} else {
							System.out.println("It's a wall ! Try again");
							System.out.println(serverGameInt.displayNeighbour(roomPlayer));
							System.out.print("Where do you want to go ? [N/E/S/O] : ");
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
