package model;

import rmi.Twitter;
import javax.jms.Session;
import javax.naming.InitialContext;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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
			twitter.newAccount("Fer", "123");
			twitter.newAccount("Tom", "123");

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		// this.simulate3();
	}

	public void simulate1() {
		try {
			// Account creation and login
			System.out.println("### SIMUL 1 ###");
			System.out.println(twitter.connect("Popol", "zizi"));
			System.out.println(twitter.newAccount("Popol", "zizi"));
			User u = twitter.connect("Popol", "zizi");
			System.out.println(u);

			this.user = new UserClient(twitter, u);

			// Hashtags creation tests
			System.out.println("### HASHTAGS ###");
			System.out.println(twitter.createHashtag("penis"));
			System.out.println(twitter.createHashtag("penis"));
			System.out.println(twitter.createHashtag("peis"));

			user.followHashtag("peis");

			System.out.println("at the end :");
			System.out.println(twitter.connect("Popol", "zizi"));

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void simulate2() {
		try {
			System.out.println("### SIMUL 2 ###");
			System.out.println(twitter.connect("JEAN", "trucucucul"));
			System.out.println(twitter.newAccount("JEAN", "trucucucul"));
			User u = twitter.connect("JEAN", "trucucucul");
			System.out.println(u);

			this.user = new UserClient(twitter, u);

			System.out.println(user.followUser("Popol"));

			System.out.println(twitter.connect("JEAN", "trucucucul"));

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void simulate3() {
		try {
			System.out.println("### SIMUL 3 ###");
			System.out.println(twitter.connect("Princesse", "j<3LesPenis"));
			System.out.println(twitter.newAccount("Princesse", "j<3LesPenis"));
			User u = twitter.connect("Princesse", "j<3LesPenis");
			System.out.println(u);

			this.user = new UserClient(twitter, u);

			user.postMessage("parce que #peis bonjour @Popol", false);
			user.postMessage("Je tweet @JEAN", false);
			user.postMessage("parce que #peis dd", false);

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

	// TODO
	public void followHashtag(String text) throws RemoteException {
		ArrayList<String> temp = new ArrayList<>();
		for (int i = 0; i < user.getMessages().size(); i++) {
			if (user.getMessages().get(i).contains(text)) {
				temp.add(user.getMessages().get(i));
			}
		}

		setChanged();
		notifyObservers(temp);
	}

	// TODO
	public void followUser(String text) throws RemoteException{
		/*ArrayList<String> temp = new ArrayList<>();
		System.out.println(user.getMessages().toString());
		for (int i = 0; i < user.getMessages().size(); i++) {
			if (user.getMessages().get(i).contains(text)) {
				temp.add(user.getMessages().get(i));
			}
		}*/
		user.followUser(text);
		//setChanged();
		//notifyObservers(temp);
	}
}
