package chat;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServerInterface {

	private static final long serialVersionUID = 1L;
	private Vector<ChatClientInterface> users;

	protected ChatServerImpl() throws RemoteException {
		users = new Vector<ChatClientInterface>();

	}
	
	
	public synchronized void joinChatRoom(ChatClientInterface c) throws RemoteException {
		users.add(c);

	}

	public synchronized void leaveChatRoom(ChatClientInterface c) throws RemoteException {
		users.remove(c);

	}

	public synchronized void sendMessage(String s) throws RemoteException {
		for (int i = 0; i < users.size(); i++) {
			users.get(i).getMessage(s);
		}
	}

	public String[] getWhoisOnline() throws RemoteException {
		String[]allUsers = new String[users.size()];
		for(int i = 0; i< allUsers.length; i++){
			//allUsers[i] = users.elementAt(i).getPseudo();
		}
		return allUsers;
	
	 }




}
