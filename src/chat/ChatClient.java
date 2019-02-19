package chat;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.print.DocFlavor.STRING;

import maze.Player;

public class ChatClient implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private ChatServerInterface chatSrv;

	/**
	 * Contructor of ChatClient class
	 * @param serverChatInt
	 * @param name
	 * @throws RemoteException
	 */
	public ChatClient(ChatServerInterface serverChatInt, String name) throws RemoteException{
		this.chatSrv = serverChatInt;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}
