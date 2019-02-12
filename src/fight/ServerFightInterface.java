package fight;

import java.rmi.Remote;
import java.rmi.RemoteException;

import maze.People;

public interface ServerFightInterface extends Remote {
	void initializeFight(People p1, People p2) throws RemoteException;
	People turn() throws RemoteException;
}
