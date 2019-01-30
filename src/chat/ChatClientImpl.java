package chat;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import maze.Player;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClientInterface, Runnable {

	private static final long serialVersionUID = 1L;
	private ChatServerInterface chatS;

	public Player player;

	public ChatClientImpl(ChatServerInterface cs, String username) throws RemoteException {
		this.chatS = cs;
		player = new Player(username);
		cs.joinChatRoom(this);
	}



	public synchronized void getMessage(String s) throws RemoteException {
		System.out.println(s);
	}

	public void run() {
		System.out.println("You are "+ player.getName());
		Scanner in = new Scanner(System.in);
		String msg;

		while (true) {
			try {
				msg = in.nextLine();
				msg="["+player.getName()+"] "+msg;	
				chatS.sendMessage(msg);
			} catch (Exception e) {
				System.err.println("Problem run");
				e.printStackTrace();
			}
		}
	}
	
	
}
