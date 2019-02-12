package main;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

import chat.ChatClientImpl;
import chat.ChatServerInterface;
import fight.Fight;
import fight.ServerFightImpl;
import fight.ServerFightInterface;
import maze.Direction;
import maze.Monster;
import maze.People;
import maze.Room;
import maze.Player;


public class Client{
	private static Player player = null;
	private static Room roomPlayer = null;
	private static ServerGameInterface serverGameInt;
	private static ChatServerInterface serverChatInt;
	private static ServerFightInterface serverFightInt;

	private static ArrayList<Player> playersInSameRoom;
	private static Scanner scanner;

	public static void main(String args[]) throws RemoteException{

		String urlChat = "rmi://localhost/ChatServer";
		String urlGame = "rmi://localhost/Game";
		String urlFight = "rmi://localhost/FightServer";


		try {  
			// Getting the registry 
			Registry registryGame = LocateRegistry.getRegistry(1099);
			// Looking up the registry for the remote object 
			serverGameInt = (ServerGameInterface) Naming.lookup(urlGame); 
			serverChatInt = (ChatServerInterface) Naming.lookup(urlChat);
			serverFightInt = (ServerFightInterface) Naming.lookup(urlFight);

			System.err.println("Server connected");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString()); 
			e.printStackTrace();
		} 

		String chaine = "";
		scanner = new Scanner(System.in);
		System.out.print("Enter your name - \"END\" close connection : ");

		while(!chaine.equalsIgnoreCase("END")) {
			chaine = scanner.nextLine();
			if (chaine != null) {
				if (chaine.equalsIgnoreCase("END")) {
					System.out.println("IS THE END");
					break;
				}
				if (player == null){ // Player initialization
					try {
						player = serverGameInt.createUser(chaine);
						roomPlayer = serverGameInt.getRoom(player);	
						System.out.println("You are " + player.getName() + ". Press enter to begin");
						
						playersInSameRoom =  serverGameInt.otherPlayerWithMe(roomPlayer);
					} catch (RemoteException e) {
						e.printStackTrace();
						break;
					}	
				} else { // Game
					if (chaine.startsWith("\"") && playersInSameRoom.size() != 1){ // Chat
						System.out.println("CHAT"); // DEBUG
						chaine = chaine.substring(1);
						System.out.println(chaine); // DEBUG			
					}
					else if (chaine.equals("N") || chaine.equals("E") || chaine.equals("S") || chaine.equals("O")) { // Maze
						try {
							if (serverGameInt.canMove(player, roomPlayer, chaine)) {
								roomPlayer = serverGameInt.walkPlayer(player, Direction.valueOf(chaine));
								fight(); // Start fight ! 
								if (serverGameInt.isTheEnd(roomPlayer)) {
									System.out.println("You see the light ! END");
									break;
								}
								playersInSameRoom = serverGameInt.otherPlayerWithMe(roomPlayer);
								System.out.println(playersInSameRoom.toString()); // DEBUG
							} else {
								System.out.println("It's a wall ! Try again");
							}
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
					System.out.println(serverGameInt.displayNeighbour(roomPlayer));
					System.out.print("Where do you want to go ? [N/E/S/O] or \" to speak : ");	
				}
			}
		}
	}

	private static void fight() { // TODO : attention j'update pas la liste du serveur
		try {
			serverFightInt.initializeFight(player, new Monster());
		} catch (RemoteException e) {
			System.err.println("Server connexion error");
		}
			String msg;
			People deadp = null;
			System.out.print("Start fight, run away ? [Y/N] ");

			while (true) {
				msg = scanner.nextLine();
				if (msg.equals("Y")) {
					break;
				} else {
					try {
						deadp = serverFightInt.turn();
					} catch (RemoteException e) {
						System.err.println("Server connexion error");
					}
					if (deadp == null) {
						player.lvlUp();
						try { //TODO 
							serverGameInt.updatePlayer(player);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(player.toString());
						break;
					} else if ( deadp.getPV() == 0) {
						System.out.println("You are dead !");
						System.exit(0);
					} else {
						System.out.print("Your health level : " + deadp.getPV() + "pv, run away ? [Y/N] ");
					}
				}
			}
		

	}
}
