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

	public ChatClient(ChatServerInterface serverChatInt, String s) throws RemoteException{
		this.chatSrv = serverChatInt;
		this.name = s;
	}
	
	public void getMessage(String s)  throws RemoteException {
		System.out.println(s);
	}

	public ChatClient getString()  throws RemoteException {
		return this;
	}


	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}
