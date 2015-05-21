import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * Created by Tom Veniat on 21/05/15.
 */
public class Client {

    private String host;
    private int port;
    private Twitter twitter;

    public Client(String host, int port) {
        this.host=host;
        this.port=port;
        try {
            Registry r = LocateRegistry.getRegistry(host,port);
            System.out.println(r);
            twitter= (Twitter) r.lookup("twitter");
            System.out.println(twitter.connect("a", "zizi"));
            System.out.println(twitter.newAccount("a", "zizi"));
            System.out.println(twitter.connect("a", "zizi"));
        } catch (RemoteException e) {
            e.printStackTrace();
       /* } catch (NotBoundException e) {
            e.printStackTrace();*/
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Client c = new Client("localhost",1099);

    }
}
