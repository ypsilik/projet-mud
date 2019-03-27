package main;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import javax.print.attribute.Attribute;
import javax.print.attribute.standard.Severity;

import chat.ChatServerInterface;
import fight.ServerFightInterface;
import notif.NotifImplementation;
import notif.NotifInterface;
import maze.Direction;
import maze.Monster;
import maze.People;
import maze.Player;
import maze.Room;
import maze.ServerGameInterface;

public class Client {

	private static Player player;

	private static ServerGameInterface serverGameInt;
	private static ChatServerInterface serverChatInt;
	private static ServerFightInterface serverFightInt;

	private static Scanner scanner;

	private static NotifInterface notifInt;

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
			}
		} catch (Exception e) {
			System.err.println("Server game error - please restart game");
			///e.printStackTrace();
			System.exit(-1);
		}
		notifInt = null;
		try {
			System.out.println("PLAYER : "+ player); // DEBUG
			notifInt = new NotifImplementation();
			serverGameInt.coNotif(player, notifInt);
			serverFightInt.coNotif(player, notifInt);
			serverChatInt.recordNotification(player,notifInt);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("You're ready to play " + player.getName() + "! - Press enter to begin");
		while(!chaine.equalsIgnoreCase("END")) {
			try {
				serverGameInt.coNotif(player, notifInt);
			} catch (Exception e2) {
				System.err.println("Error co notif server Game");
//				e2.printStackTrace();
			}
			if (player.getPV() == 0) {
				System.out.println("Disconnected");
				System.exit(0);
			}
			chaine = scanner.nextLine();
			if (chaine != null) {
				if (chaine.equalsIgnoreCase("END")) {
					System.out.println("End of game - Disconnected");
					System.exit(0);
				}
				try {
					serverGameInt.displayMaze(serverGameInt.getRoom(player), player);
				} catch (Exception e1) {
					System.err.println("You're dead - or server error");
					System.exit(-1);
//					e1.printStackTrace();
				}
				if (chaine.startsWith("\"") ){ // Chat
					try {
						chat(serverGameInt.getRoom(player), player, chaine);
					} catch (Exception e) {
						System.err.println("Chat server error");
						System.exit(-1);
//						e.printStackTrace();
					}
				} else if (chaine.equals("N") || chaine.equals("E") || chaine.equals("S") || chaine.equals("O")) {
						try {
							if (serverGameInt.walkPlayer(player, chaine) == 0) { // ROOM
								fight();
							} else if (serverGameInt.walkPlayer(player, chaine) == 1) { // END
								System.exit(0);
							} else { // WALL
							}
						} catch (Exception e) {
							System.err.println("Server Fight error");
							System.exit(-1);
//							e.printStackTrace();
						}
				}
			}
			System.out.print("Where do you want to go ? [N/E/S/O] or \" to speak : ");
		}
	}

	private static void fight() throws Exception {
		serverFightInt.coNotif(player, notifInt);
		String msg;
		for (Monster monster : serverGameInt.getRoom(player).getMonsters()) {
			System.out.println( monster.getId() + " -> " + monster); // TODO : Change display style
		}
		for (Player playerF : serverGameInt.getRoom(player).getPlayers()) {
			if (!playerF.getName().equals(player.getName())) {
				System.out.println( playerF.getName() + " -> " + playerF); // TODO : Change display style
			}
		}
		System.out.println("Choose monster to fight [Monster <id>] or player [Player <name>]");
		msg = scanner.nextLine();
		if (msg.split(" ")[0].equalsIgnoreCase("Monster")) {
			Monster monster = serverGameInt.getRoom(player).searchMonster(Integer.parseInt(msg.split(" ")[1]));
			serverFightInt.initializeFight(player, monster, notifInt);
			System.out.print("Start fight, run away ? [Y/N] ");
			fightTurn();	
			return;
		} else if (msg.split(" ")[0].equalsIgnoreCase("Player")) {
			Player playerF = serverGameInt.getRoom(player).searchPlayer(msg.split(" ")[1]);
			serverGameInt.notifyRival(player, playerF, "Player " + player.getName() + " start fight with you (Are you lucky ?)"); // TODO modif phrase XD
			serverFightInt.initializeFight(player, playerF, notifInt);
			System.out.print("Start fight, run away ? [Y/N] ");
			fightTurn();
			return;
		}
	}

	private static void fightTurn() {
		String msg;
		while (true) {
			People whoIsDead = null;
			msg = scanner.nextLine();
			if (msg.equals("Y")) {
				return;
			} else {
				try {
					serverFightInt.coNotif(player, notifInt);
					whoIsDead = serverFightInt.turn();
				} catch (Exception e) {
					System.err.println("Server connexion error");
				}
				if (((whoIsDead instanceof Monster) ||
						((whoIsDead instanceof Player) && !((Player) whoIsDead).getName().equals(player.getName()))) && 
						whoIsDead.getPV() == 0) {
					player.lvlUp();
					System.out.println("You're alive !");
					try {
						serverGameInt.updatePlayer(player, whoIsDead, serverGameInt.getRoom(player));
						System.out.println(player);
					} catch (Exception e) {
						System.err.println("Update player error");
//						e.printStackTrace();
					}
					return;
				} else if (((whoIsDead instanceof Player) &&
						((Player) whoIsDead).getName().equals(player.getName())) &&
						whoIsDead.getPV() == 0) {
					System.out.println("You are dead !");
					try {
						serverGameInt.removeUser(player);
					} catch (Exception e) {
						System.err.println("Player deletion suppression error");
//						e.printStackTrace();
					}
					System.exit(0);
				} else {
				}
			}
		}
	}

	private static void chat(Room r, Player p, String msg) {
		try {
			serverChatInt.recordNotification(player, notifInt);
		} catch (Exception e) {
			System.err.println("Chat error");
//			e.printStackTrace();
		}
		try { // send msg attribute
			serverChatInt.sendMessage(r,p,msg);	
		} catch (Exception e1) {
			System.err.println("Chat error");
//			e1.printStackTrace();
		}

	}

}
