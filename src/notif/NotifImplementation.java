package notif;

// Notification (srv envoi msg au client)

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NotifImplementation extends UnicastRemoteObject implements NotifInterface {
	public NotifImplementation() throws RemoteException {
		super();
	}

	public void notify(String notication) {
		System.out.println(notication);
	}
}
