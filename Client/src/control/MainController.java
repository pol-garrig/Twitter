package control;

import java.rmi.RemoteException;

import model.Client;
import model.UserClient;

import view.AddFollowing;
import view.ConnectionFrame;
import view.ErrorRemote;
import view.ErrorView;
import view.FollowersView;
import view.FollowingView;
import view.HashtagView;
import view.NewAccount;
import view.TwitterView;

public class MainController {

	private Client c;
	private UserClient uc;

	public MainController(Client c) {
		this.c = c;
	}

	public void ConnectionToNewAccount() {
		System.out.println("NewAccount");

		NewAccount nw = new NewAccount(this);
	}

	public void NewAccountToConnection(String user, String password) {
		try {
			c.newAccount(user, password);
			System.out.println("ConnectionFrame");
			ConnectionFrame nw = new ConnectionFrame(this);
		} catch (RemoteException e) {
			e.getStackTrace();
			ErrorRemote er = new ErrorRemote(this);
		}

	}

	public void createNewUser(String lastname, String firstname,
			String password, String user) {
		System.out.println("new user created");
	}

	public void ConnectionToTwitter(String user, String password) {
		try {
			c.connect(user, password);
			TwitterView tx = new TwitterView(this, user);
			c.addObserver(tx);
			c.getUserClient().addObserver(tx);

		} catch (RemoteException e) {
			e.getStackTrace();
			ErrorRemote er = new ErrorRemote(this);
		}
	}

	public void ErrorView() {
		ErrorView e = new ErrorView(this);
	}

	public void TwitterToFollowersView() {
		FollowersView f = new FollowersView(this);
	}

	public void TwitterToFollowingView() {
		FollowingView f = new FollowingView(this);
	}

	public void FollowingToAdd() {
		AddFollowing a = new AddFollowing(this);
	}

	public void newAccount(String user, String pssd) {
		try {
			c.connect(user, pssd);
		} catch (RemoteException e) {
			e.getStackTrace();
			ErrorRemote er = new ErrorRemote(this);
		}
	}

	public void postMessage(String msn) {
		;
		c.postMessage(msn);
	}

	public void TwitterToHashtagView() {
		HashtagView h = new HashtagView(this);
	}

	public Client getUser() {
		return c;
	}

	public void followHashtag(String text) {
		try {
			c.followHashtag(text);
		} catch (RemoteException e) {
			e.getStackTrace();
			ErrorRemote er = new ErrorRemote(this);
		}
	}

	public void followUser(String text) {
		try {
			c.followUser(text);
		} catch (RemoteException e) {
			e.getStackTrace();
			ErrorRemote er = new ErrorRemote(this);
		}
		
	}

}
