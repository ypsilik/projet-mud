package notif;

// Notification interface

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotifInterface extends Remote {

	void notify(String notification) throws RemoteException;
}
