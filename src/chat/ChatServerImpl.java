package chat;

import maze.Player;
import maze.Room;
import maze.ServerGameImplementation;
import chat.ChatServerInterface;
import notif.NotifInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServerInterface {

	private static final long serialVersionUID = 1L;
	private ServerGameImplementation maze;
	private Player player;
	private HashMap<String, Player> listPlayer;
	/**
	 * Constructor of ChatServeRImplementation
	 * @param maze
	 * @throws RemoteException
	 */
	public ChatServerImpl(ServerGameImplementation maze) throws RemoteException {
		super();
		this.maze = maze;
		this.listPlayer = new HashMap<String,Player>();
	}


	/**
	 * send message in room
	 * @param roomA : room
	 * @param client : Player
	 * @param text : text
	 */
	public synchronized void sendMessage(Room roomA, Player playerA ,String msg) throws RemoteException {
		for (Player player : maze.getRoom(playerA).getPlayers()) {
			listPlayer.put(player.getName(), player);
		}
		//		listPlayer = maze.allPlayerInRoomChat(roomA);
		System.out.println("CHAT : " + listPlayer + " ------ ROOM : " + maze.getRoom(playerA)); // DEBUG
		for (HashMap.Entry<String, Player> entry : listPlayer.entrySet()) { 
			String key = entry.getKey();
			System.out.println("CHAT for : " + listPlayer.get(key) + " -------- PA : " + playerA + " ---- " + msg); // DEBUG
			listPlayer.get(key).getServeurNotif().notify(playerA.getName() + " : " + msg);
		}
	}

	public void recordNotification(Player playerA, NotifInterface notif) throws RemoteException {
		for (Player player : maze.getRoom(playerA).getPlayers()) {
			listPlayer.put(player.getName(), player);
		}
		Player player = listPlayer.get(playerA.getName());
		player.setServerNotif(notif);	
	}
}



