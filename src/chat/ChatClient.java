import java.rmi.Naming;

public class ChatClient {

	public static void main(String[] args) {
		String url = "rmi://localhost/ChatServer";
		try {
			ChatServerInterface cs = (ChatServerInterface) Naming.lookup(url);
			new Thread(new ChatClientImpl(cs)).start();
		} catch (Exception e) {
			System.err.println("Problem main");
		}
	}

}
