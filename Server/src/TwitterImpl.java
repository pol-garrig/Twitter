import model.Hashtag;
import model.User;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public class TwitterImpl extends UnicastRemoteObject implements Twitter {

    private List<Hashtag> hashtags;
    private List<User> users;
    private InitialContext context;
    private Connection connection;
    private Session session;

    public TwitterImpl() throws RemoteException {
        hashtags = new ArrayList<Hashtag>();
        users=new ArrayList<User>();

        Hashtable properties = new Hashtable();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");

        try {
            context = new InitialContext(properties);
            javax.jms.ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
            connection = factory.createConnection();
            session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean newAccount(String username, String password) throws RemoteException {
        if (existsUser(username)) return false;
        users.add(new User(username, password, session));
        try {

            MessageProducer producer =session.createProducer((Topic) context.lookup("dynamicTopics/topics"));
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("Type","MAJ");
            mapMessage.setString("TargetType","user");
            mapMessage.setString("TargetName",username);
            producer.send(mapMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public User connect(String username, String password) throws RemoteException {
        return (loggedUser(username, password));
    }

    @Override
    public boolean createHashtag(String hashtag) throws RemoteException{
        System.out.println("Creating Hashtag : " + hashtag);

        if (findHashtag(hashtag) != null) return false;

        //Creating the topic and posting the first message.
        Topic t = null;
        try {
            t = (Topic) context.lookup("dynamicTopics/"+hashtag);

            hashtags.add(new Hashtag(hashtag));
            MessageProducer sender = session.createProducer(t);
            MapMessage mess = session.createMapMessage();
            mess.setString("Author", "TwitterAdmin");
            mess.setString("Content", "New hashtag !");
            sender.send(mess);
            sender.close();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean followHashtag(String username, String hashtag) throws RemoteException {
        System.out.println("###Add of #"+hashtag+" to "+username);
        Hashtag hash = findHashtag(hashtag);
        if (hash == null) return false;
        User user = findUser(username);
        if (user==null) return false;


        user.followHastag(hash);
        System.out.println("#Add END : " + user);
        return true;
    }

    @Override
    public boolean followUser(String username, String followedUser) throws RemoteException {
        System.out.println("###Add of @"+followedUser+" to "+username);
        User user = findUser(username), wantedUser = findUser(followedUser);
        if (user == null || wantedUser == null){
            System.out.println("One of these guys is null.");
            return false;
        }

        user.followUser(wantedUser);
        System.out.println("#Add END : " + user);
        return true;
    }

    @Override
    public List<User> allUsers() throws RemoteException {
        return users;
    }

    @Override
    public List<Hashtag> allHashtags() throws RemoteException {
        return hashtags;
    }

    @Override
    public boolean existsTarget(String targetName) throws RemoteException {
        return existsUser(targetName) || existsHashtag(targetName);
    }




//    @Override
//    public boolean postMessage(User user, String message, String hashtag) throws RemoteException {
//        Hashtag hash = findHashtag(hashtag);
//        if (hash == null) return false;
//        MessageProducer mp = null;
//        try {
//            mp = session.createProducer(hash.destination);
//            MapMessage newMessage = session.createMapMessage();
//            newMessage.setString("Author",user.getUsername());
//            newMessage.setString("Content", message);
//
//            if (mp != null){
//                mp.send(newMessage);
//                System.out.println("message sent.");
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }



    private User findUser(String username){
        for (User u : users)
            if (u.getUsername().equals(username))
                return u;
        return null;
    }

    @Override
    public boolean existsUser(String username){
        return findUser(username)!=null;
    }

    private Hashtag findHashtag(String hashtag){
        for (Hashtag h : hashtags)
            if (h.getName().equals(hashtag))
                return h;
        return null;
    }

    @Override
    public boolean existsHashtag(String hashtagName) {
        return findHashtag(hashtagName)!=null;
    }

    private User loggedUser(String username, String password) {
        for (User u : users)
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return u;
        return null;
    }

    @Override
    public String toString(){
        return "Je suis un twitter de "+users.size()+" users et "+hashtags.size()+" hastags.";
    }

}
