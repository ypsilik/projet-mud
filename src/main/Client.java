package main;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import javax.print.attribute.standard.Severity;

import chat.ChatServerInterface;
import fight.ServerFightInterface;
import notif.NotifImplementation;
import notif.NotifInterface;
import maze.Direction;
import maze.Monster;
import maze.Player;
import maze.Room;
import maze.ServerGameInterface;

// Client Jeu

public class Client {

	private static Player player;

	private static ServerGameInterface serverGameInt;
	private static ChatServerInterface serverChatInt;
	private static ServerFightInterface serverFightInt;

	private static Scanner scanner;

	public static void main(String[] args) {
		// INIT
		String urlChat = "rmi://localhost/ChatServer";
		String urlGame = "rmi://localhost/Game";
		String urlFight = "rmi://localhost/FightServer";

		try {
			// Getting the registry
			Registry registryGame = LocateRegistry.getRegistry(1099);
			// Looking up the registry for the remote object
			serverGameInt = (ServerGameInterface) Naming.lookup(urlGame); // co game
			serverChatInt = (ChatServerInterface) Naming.lookup(urlChat); // co chat
			serverFightInt = (ServerFightInterface) Naming.lookup(urlFight); // co fight
			System.err.println("Server connected");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}

		// Player initialization
		String chaine = "";
		scanner = new Scanner(System.in);
		System.out.print("Enter your name - \"END\" close connection : ");
		chaine = scanner.nextLine();

		try{
			if (serverGameInt.checkPlayerExists(chaine) != null) {
				player = serverGameInt.getPlayer(chaine);
			} else {
				player = serverGameInt.createUser(chaine);
				serverGameInt.initPlayer(player);

				//roomPlayer = serverGameInt.getRoom(player);
				//  chatClient = new ChatClient(serverChatInt,player.getName());
				//  playersInSameRoom =  serverGameInt.otherPlayerWithMe(roomPlayer);
				//    serverChatInt.joinChatRoom(player.getName(), roomPlayer);
				//    System.out.println(roomPlayer); // DEBUG
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		// this.remoteChat.seConnecter(this.personnage); // Co Chat
		// CO DE LA NOTIF

		NotifInterface notifInt = null;
		try {
			System.out.println("PLAYER : "+ player); // DEBUG
			notifInt = new NotifImplementation();
			serverGameInt.coNotif(player, notifInt);
			serverFightInt.coNotif(player, notifInt);
			serverChatInt.recordNotification(player,notifInt);

		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		// 		this.remoteChat.enregistrerNotification(this.personnage, remoteNotif);
		System.out.println("You're ready to play " + player.getName() + "!");
		while(!chaine.equalsIgnoreCase("END")) {
			try {
				serverGameInt.coNotif(player, notifInt);
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if (chaine != null) {
				if (chaine.equalsIgnoreCase("END")) {
					System.out.println("IS THE END");
					break;
				}
				try {
					serverGameInt.displayMaze(serverGameInt.getRoom(player), player);
				} catch (RemoteException e1) {

					e1.printStackTrace();
				}
				System.out.print("Where do you want to go ? [N/E/S/O] or \" to speak : ");
				chaine = scanner.nextLine();
				if (chaine.startsWith("\"") ){ // Chat
					try {
						chat(serverGameInt.getRoom(player), player, chaine);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (chaine.equals("N") || chaine.equals("E") || chaine.equals("S") || chaine.equals("O")) {
					try {
						if (!serverGameInt.walkPlayer(player, chaine)) { // END TODO : change function tu return 0, 1 or 2 if it's END, wall or door
							System.exit(0);
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					try {
						fight(); // TODO : if it's wall no start fight
					} catch (RemoteException e) {
						e.printStackTrace();
					} 
				}
			}
		}
	}

	private static void fight() throws RemoteException {
		String msg;
		int index = 0;
		for (Monster monster : serverGameInt.getRoom(player).getMonsters()) {
			System.out.println( ++index + " -> " + monster); // TODO : Change display style
		}
		System.out.println("Choose monster to fight");
		msg = scanner.nextLine();
		Monster monster = serverGameInt.getRoom(player).getMonsters().get(Integer.parseInt(msg) - 1);
		serverFightInt.initializeFight(player, monster);
		System.out.print("Start fight, run away ? [Y/N] ");

		while (true) {
			Player playerIsDead = null;
			msg = scanner.nextLine();
			if (msg.equals("Y")) {
				break;
			} else {
				try {
					playerIsDead = serverFightInt.turn();
				} catch (RemoteException e) {
					System.err.println("Server connexion error");
				}
				if (playerIsDead == null) {
					player.lvlUp();
					System.out.println("You're alive !");
					try {
						serverGameInt.updatePlayer(player, monster, serverGameInt.getRoom(player));
						System.out.println(player);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					break;
				} else if (playerIsDead.getPV() == 0) {
					System.out.println("You are dead !");
					try {
						serverGameInt.removeUser(player);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					System.exit(0);
				} else {
					System.out.print("Your health level : " + playerIsDead.getPV() + "pv, run away ? [Y/N] ");
				}
			}
		}
	}
	
	private static void chat(Room r, Player p, String msg) {
		try { // send msg attribute
			serverChatInt.sendMessage(r,p,msg);	
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
