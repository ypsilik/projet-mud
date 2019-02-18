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

	public ChatServerImpl(Maze maze) throws RemoteException {
		this.maze = maze;
		this.availableClient = new HashMap<ChatClient, Room>();
		this.rooms = new ArrayList<Room>();
	}


	public void joinChatRoom(String c, Room r) throws RemoteException {
		System.out.println("1"); // DEBUG 
		ChatClient ChatClient = checkClientExist(c.toString());
		System.out.println("2"); // DEBUG
		Room room = maze.checkRoomExist(r.getPosition().getX(), r.getPosition().getY());
		System.out.println("3"); // DEBUG
		if (room == null) {
			rooms.add(r);

			System.out.println("ROOM : " + r); // DEBUG
			availableClient.put(ChatClient, r);

		} else {
			rooms.add(room);

			System.out.println("ROOM 2 " + room); // DEBUG
			availableClient.put(ChatClient, room);
		}
		System.out.println("TABLEAU CLIENT : " + availableClient); // DZBUG
	}

	private ChatClient checkClientExist(String name) {
		if (availableClient.isEmpty()) {
			try {
				return new ChatClient(this,name);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}


	public synchronized void leaveChatRoom(Room r) throws RemoteException {
		rooms.remove(r);
	}

	public synchronized void sendMessage(Room r, ChatClient c,String s) throws RemoteException {
		ArrayList<ChatClient> clients = allClientInRoom(r);
		System.out.println("CLIENTS : " + clients); // DEBUG
		for (int i = 0; i < clients.size(); i++) {
			clients.get(i).getMessage(s);
		}
	}


	private ArrayList<ChatClient> allClientInRoom(Room r) {
		ArrayList<ChatClient> clientsSameRoom = new ArrayList<ChatClient>(); 
		for (HashMap.Entry<ChatClient, Room> entry : availableClient.entrySet()) { 
			ChatClient key = entry.getKey();
			Room value = entry.getValue();
			if ((value.getPosition().getX() == r.getPosition().getX()) && (value.getPosition().getY() == r.getPosition().getY())){
				clientsSameRoom.add(key);
			}
		}
		return clientsSameRoom;
	}
}

