package chat;

import maze.Maze;
import maze.Player;
import maze.Room;
import chat.ChatServerInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import main.Client;
import main.ServerGame;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServerInterface {

	private static final long serialVersionUID = 1L;
	private ArrayList<Room> rooms;
	private Maze maze;
	private HashMap<ChatClient, Room> availableClient;
	private HashMap<Room, String> msgReply;

	/**
	 * Constructor of ChatServeRImplementation
	 * @param maze
	 * @throws RemoteException
	 */
	public ChatServerImpl(Maze maze) throws RemoteException {
		this.maze = maze;
		this.availableClient = new HashMap<ChatClient, Room>();
		this.rooms = new ArrayList<Room>();
		this.msgReply = new HashMap<Room,String>();
	}

	/**
	 * Add client to room
	 * @param clientName : client name
	 * @param roomA : Room
	 */
	public void joinChatRoom(String clientName, Room roomA) throws RemoteException {
		ChatClient ChatClient = checkClientExist(clientName.toString());
		Room room = maze.checkRoomExist(roomA.getPosition().getX(), roomA.getPosition().getY());
		if (room == null) {
			rooms.add(roomA);

			System.out.println("ROOM : " + roomA); // DEBUG
			availableClient.put(ChatClient, roomA);

		} else {
			rooms.add(room);

			System.out.println("ROOM 2 " + room); // DEBUG
			availableClient.put(ChatClient, room);
		}
		System.out.println("TABLEAU CLIENT : " + availableClient); // DZBUG
	}

	/**
	 * Check if client exists in Chat server
	 * @param name
	 * @return ChatClient
	 */
	private ChatClient checkClientExist(String name) {
		if (availableClient.isEmpty()) {
			try {
				return new ChatClient(this,name);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			for (HashMap.Entry<ChatClient, Room> entry : availableClient.entrySet()) { 
				ChatClient key = entry.getKey();
				if (key.toString().equalsIgnoreCase(name)) {
					return key;
				}
			}
			try {
				return new ChatClient(this,name);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * send message in room
	 * @param roomA : room
	 * @param client : Chatclient
	 * @param text : text
	 */
	public synchronized void sendMessage(Room roomA, ChatClient client,String text) throws RemoteException {
		ArrayList<ChatClient> clients = allClientInRoom(roomA);
		String rep = "[" + client.toString() + "] " + text;
		msgReply.put(roomA,rep);
	}

	/**
	 * return all client in maze room
	 * @param roomA
	 * @return list of client
	 */
	private ArrayList<ChatClient> allClientInRoom(Room roomA) {
		ArrayList<ChatClient> clientsSameRoom = new ArrayList<ChatClient>(); 
		for (HashMap.Entry<ChatClient, Room> entry : availableClient.entrySet()) { 
			ChatClient key = entry.getKey();
			Room value = entry.getValue();
			if ((value.getPosition().getX() == roomA.getPosition().getX()) && (value.getPosition().getY() == roomA.getPosition().getY())){
				clientsSameRoom.add(key);
			}
		}
		return clientsSameRoom;
	}

	/**
	 * get client message to send other client
	 * @param roomPlayer
	 * @return list of messages
	 */
	public ArrayList<String> getReply(Room roomPlayer) throws RemoteException {
		ArrayList<String> repliesByRoom = new ArrayList<String>();
		for (HashMap.Entry<Room, String> entry : msgReply.entrySet()) { 
			Room key = entry.getKey();
			String value = entry.getValue();
			if ((key.getPosition().getX() == roomPlayer.getPosition().getX()) && (key.getPosition().getY() == roomPlayer.getPosition().getY())){
				repliesByRoom.add(value);
			}
		}
		return repliesByRoom;
	}
}

