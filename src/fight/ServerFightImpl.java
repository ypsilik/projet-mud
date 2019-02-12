package fight;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import chat.ChatServerInterface;
import maze.People;

public class ServerFightImpl  extends UnicastRemoteObject implements ServerFightInterface{

	private static final long serialVersionUID = 1L;
	private People p1;
	private People p2;

	public ServerFightImpl() throws RemoteException {
		super();
	}

	public void initializeFight(People p1, People p2)  throws RemoteException {
		this.p1 = p1;
		this.p2 = p2;
	}

	public People turn() throws RemoteException{
		return new Fight(p1, p2).start();
	}
}
