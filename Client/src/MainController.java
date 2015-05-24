package control;

import view.AddFollowing;
import view.ConnectionFrame;
import view.ErrorView;
import view.FollowersView;
import view.FollowingView;
import view.NewAccount;
import view.TwitterView;

public class MainController {

	public MainController() {

	}

	public void ConnectionToNewAccount() {
		System.out.println("NewAccount");
		NewAccount nw = new NewAccount(this);
	}

	public void NewAccountToConnection() {
		System.out.println("ConnectionFrame");
		ConnectionFrame nw = new ConnectionFrame(this);
	}

	public void createNewUser(String lastname, String firstname,
			String password, String user) {
		System.out.println("new user created");
	}

	public void ConnectionToTwitter(String user, String password) {
		TwitterView tx = new TwitterView(this, user);
	}

	public void ErrorView() {
		ErrorView e = new view.ErrorView(this);
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
}
