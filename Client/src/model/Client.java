package model;

import datas.User;
import rmi.Twitter;

import javax.jms.Session;
import javax.naming.InitialContext;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Observable;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public class Client extends Observable {

	private InitialContext context;
	private Session session;
	private Twitter twitter;
	private UserClient user;

	public Client(String host, int port) {

		try {
			Registry r = LocateRegistry.getRegistry(host, port);
			this.twitter = (Twitter) r.lookup("twitter");

			this.initDemo();


		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}

	private void initDemo() {
		try {
			twitter.newAccount("Fer", "123");
			twitter.newAccount("Tom", "123");
			twitter.createHashtag("AppRep");
			twitter.followHashtag("Tom","AppRep");
			twitter.followUser("Fer","Tom");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}


	public void connect(String uss, String pssd) throws RemoteException {
		User u = twitter.connect(uss, pssd);
		this.user = new UserClient(twitter, u);

	}

	public UserClient getUserClient() {
		return user;
	}

	public void postMessage(String msn) {
		user.postMessage(msn, true);
		System.out.println("Liste msg = " + user.getMessages());
		setChanged();
		notifyObservers(user.getMessages());
	}

	public void newAccount(String user2, String password)
			throws RemoteException {
		twitter.newAccount(user2, password);
	}


	public void followHashtag(String text) throws RemoteException {
		user.followHashtag(text);
	}

	public void followUser(String text) throws RemoteException{

		user.followUser(text);
	}
}
