import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import maze.Player;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClientInterface, Runnable {

	private static final long serialVersionUID = 1L;
	private ChatServerInterface chatS;
	
	public Player player;

	protected ChatClientImpl(ChatServerInterface cs) throws RemoteException {
		this.chatS = cs;
		cs.joinChatRoom(this);
	}


	
	public synchronized void getMessage(String s) throws RemoteException {
		String pseudo = player.getName();
		System.out.println(pseudo + s);

	}

	public void run() {
		Scanner in = new Scanner(System.in);
		String msg;

		while (true) {
			try {
				msg = in.nextLine();
				chatS.sendMessage(msg);
			} catch (Exception e) {
				System.err.println("Problem run");
			}
		}

	}

	

	

}
