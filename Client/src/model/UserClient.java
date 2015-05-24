package model;

import datas.Hashtag;
import datas.User;
import rmi.Twitter;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Observable;

/**
 * Created by Tom Veniat on 22/05/15.
 */
public class UserClient extends Observable implements MessageListener {

	private InitialContext context;
	private Session session;
	private Twitter twitter;
	private User user;
	private List<String> hashtags;
	private List<String> users;
	private List<String> messages;

	public UserClient(Twitter twitter, User user) {
		Hashtable properties = new Hashtable();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");

		try {

			this.context = new InitialContext(properties);
			javax.jms.ConnectionFactory factory = (ConnectionFactory) context
					.lookup("ConnectionFactory");
			Connection connection = factory.createConnection();

			// connection.setClientID(user.getUsername());
			System.out.println("----->" + connection.getClientID());
			;

			this.session = connection.createSession(false,
					javax.jms.Session.AUTO_ACKNOWLEDGE);
			connection.start();

			this.hashtags = new ArrayList<>();
			for (Hashtag h : twitter.allHashtags())
				hashtags.add(h.getName());

			this.users = new ArrayList<>();
			for (User u : twitter.allUsers())
				users.add(u.getUsername());

			this.twitter = twitter;

			this.user = user;

			this.messages = new ArrayList<>();

			for (Hashtag h : user.getFollowedHashtags()) {
				listenTopic(h.getName());
			}
			for (User u : user.getFollowedUsers()) {
				listenTopic(u.getUsername());
			}

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Abonne un utilisateur a un topic.
	 *
	 * @param name
	 * @return
	 */
	private boolean listenTopic(String name) {
		System.out.println("On suit un nouveau tag");

		MessageConsumer consumer;
		try {
			consumer = session.createConsumer((Topic) context
					.lookup("dynamicTopics/" + name));
			consumer.setMessageListener(this);
			user=twitter.connect(user.getUsername(),user.getPassword());
		} catch (NamingException e) {
			e.printStackTrace();
			return false;
		} catch (JMSException e) {
			e.printStackTrace();
			return false;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("Now listening.");
		return true;
	}

	/**
	 * Le message à envoyer est parsé afin de récuperer la liste des hashtags utilisés
	 * et la liste des utilistauers mentionnés.
	 * Si un hashtag n'existe pas, il sera créé si "createHashtag" vaut true.
	 * Le message sera posté dans les topics associés à chacun des utilisateurs mentionnés (s'ils existent)
	 * ainsi que dans les topics associès aux hashtags.
	 *
	 * @param message le message a envoyer
	 * @param createHashtags Si l'on crée le hashtag à la volée s'il n'éxiste pas déjà ( non utilisé dans cette version).
	 */
	public void postMessage(String message, boolean createHashtags) {
		String userDestinations[]={};
		String hashtagDestinations[]={};
		if (message.contains("@"))
			userDestinations = message.split("@");
		if (message.contains("#"))
			hashtagDestinations = message.split("#");


		for (int i = 0; i < userDestinations.length; i++)
			userDestinations[i] = userDestinations[i].split(" ")[0];
		for (int i = 0; i < hashtagDestinations.length; i++)
			hashtagDestinations[i] = hashtagDestinations[i].split(" ")[0];

		List<String> trueDestinations = new ArrayList<>();

		try {
			for (String potentialDest : userDestinations)
				if (twitter.existsUser(potentialDest))
					trueDestinations.add(potentialDest);
			if (createHashtags) {
				for (String potentialDest : hashtagDestinations) {
					if (!twitter.existsHashtag(potentialDest))
						twitter.createHashtag(potentialDest);
					trueDestinations.add(potentialDest);
				}
			} else {
				for (String potentialDest : hashtagDestinations)
					if (twitter.existsHashtag(potentialDest))
						trueDestinations.add(potentialDest);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		for (String destination : trueDestinations) {
			postMessage(message, destination);
			System.out.println("Posting in : " + destination);
		}

		postMessage(message, user.getUsername());
		System.out.println("Posting in : " + user.getUsername());

		setChanged();
		notifyObservers(getMessages());
	}

	public boolean postMessage(String message, String destinationName) {
		try {
			MapMessage newMessage = session.createMapMessage();
			newMessage.setString("Type", "tweet");
			newMessage.setString("Author", user.getUsername());
			newMessage.setString("Content", message);

			return postMessage(newMessage, destinationName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean postMessage(Message message, String destinationName) {
		try {
			MessageProducer producer = session.createProducer((Topic) context
					.lookup("dynamicTopics/" + destinationName));
			producer.send(message);
			System.out.println("message sent.");
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean createHashtag(String hashtag) {
		try {
			return twitter.createHashtag(hashtag);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Fonction associant l'utilisateur à un hashtag,
	 * Cette fonction appelle le server pour créer l'abonnement.
	 *
	 * @param hashtag
	 * @return
	 */
	public boolean followHashtag(String hashtag) {
		try {
			if (twitter.followHashtag(user.getUsername(), hashtag))
				return listenTopic(hashtag);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Fonction abonnant un utilisateur à un autre utilistaeur,
	 * Cette fonction appelle le server pour créer l'abonnement.
	 *
	 * @param followedUser
	 * @return
	 */
	public boolean followUser(String followedUser) {
		System.out.println(user.getUsername() + " wants to follow "
				+ followedUser);
		try {
			if (twitter.followUser(user.getUsername(), followedUser))
				return listenTopic(followedUser);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Fonction appelée à chaque reception de messages.
	 *
	 * @param message Le message reçu
	 */
	@Override
	public void onMessage(Message message) {
		try {
			MapMessage mmessage = (MapMessage) message;

			switch (mmessage.getString("Type")) {
			case "tweet":
				System.out.println("New tweet in "
						+ mmessage.getJMSDestination() + " :\n\t"
						+ mmessage.getString("Author") + " -> "
						+ mmessage.getString("Content"));
				this.messages.add(mmessage.getString("Author") + " : "
						+ mmessage.getString("Content"));
				setChanged();
				notifyObservers(getMessages());
				break;
			case "MAJ":
				String targetName = mmessage.getString("TargetName");
				switch (mmessage.getString("TargetType")) {
				case "user":
					users.add(targetName);
					System.out.println("Add of the user : " + targetName);
					break;
				case "hashtag":
					hashtags.add(targetName);
					System.out.println("Add of the hashtag : " + targetName);
					break;
				default:
					System.err.println("Message error : Invalide TargetType.");
				}
				break;
			default:
				System.err.println("Message error : Invalide Type.");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	public List<String> getMessages() {
		return messages;
	}

	public List<String> getHashtag() {
		return hashtags;
	}

	public List<String> getFollowedUsers() {
		List<String> answer =  new ArrayList<>();
		for (User u : user.getFollowedUsers())
			answer.add(u.getUsername());
		return answer;
	}

	public List<String> getFollowedHashtags() {
		List<String> answer =  new ArrayList<>();
		for (Hashtag h : user.getFollowedHashtags())
			answer.add(h.getName());
		return answer;
	}

	public User getUser() {
		return user;
	}
}
