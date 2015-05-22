import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;


/**
 * Created by Tom Veniat on 21/05/15.
 */
public class Client implements MessageListener {

    private String host;
    private int port;
    private Twitter twitter;
    private Context context;
    private Connection connection;
    private Session session;
    public Client(String host, int port) {
        this.host=host;
        this.port=port;

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


        try {
            Registry r = LocateRegistry.getRegistry(host,port);
            System.out.println(r);
            twitter= (Twitter) r.lookup("twitter");
            System.out.println(twitter.toString());

            //Account creation and login
            System.out.println("### USERS ###");
            System.out.println(twitter.connect("Popol", "zizi"));
            System.out.println(twitter.newAccount("Popol", "zizi"));
            User u = twitter.connect("Popol", "zizi");
            System.out.println(u);

            //Hashtags creation tests
            System.out.println("### HASHTAGS ###");
            System.out.println(twitter.createHashtag("penis"));
            System.out.println(twitter.createHashtag("penis"));
            System.out.println(twitter.createHashtag("peis"));

            MessageConsumer consumer;
            for (Hashtag h : u.getHashtags()) {
                consumer = session.createConsumer((Topic) context.lookup("dynamicTopics/" + h.getName()));
                consumer.setMessageListener(this);
            }
            consumer = session.createConsumer((Topic) context.lookup("dynamicTopics/penis"));
            consumer.setMessageListener(this);

            for (int i = 0; i < 3; i++) {
                postMessage(u.getUsername(), "penis", "penis");
                Thread.sleep(1000);
            }




            System.out.println(twitter.followHashtag(u.getUsername(), "peis"));
            System.out.println(twitter.connect("Popol", "zizi"));
            //System.out.println(twitter.postMessage(u, "coucou", "peis"));
//            System.out.println(twitter.getSession());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Client c = new Client("localhost",1099);

    }

    private boolean postMessage(String author, String message, String destinationName){
        try {
            MapMessage newMessage = session.createMapMessage();
            newMessage.setString("Author",author);
            newMessage.setString("Content", message);

            return postMessage(newMessage,destinationName);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean postMessage(Message message, String destinationName){
        try {
            MessageProducer producer = session.createProducer((Topic) context.lookup("dynamicTopics/" + destinationName));
            producer.send(message);
            System.out.println("message sent.");
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onMessage(Message message) {
        MapMessage mmessage = (MapMessage)message;
        try {
            System.out.println("New tweet :\n\t"+mmessage.getString("Author")+" -> "+ mmessage.getString("Content"));;
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
