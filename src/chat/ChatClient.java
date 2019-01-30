package chat;

import java.rmi.Naming;

public class ChatClient {

	public static void main(String[] args) {
		String urlChat = "rmi://localhost/ChatServer";
		String pseudo = "user2";
		try {
			ChatServerInterface cs = (ChatServerInterface) Naming.lookup(urlChat);
			new Thread(new ChatClientImpl(cs,pseudo)).start();
		} catch (Exception e) {
			System.err.println("Problem main " + e.toString());
			e.printStackTrace();
		}
	}

}
