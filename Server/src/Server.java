import rmi.Twitter;
import rmi.TwitterImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public class Server {


    public static void main(String[] args) {


        int port = 1099;
        String host = "localhost";
        if (args.length>0)
            port=Integer.valueOf(args[0]);
        if (args.length>1)
            host=args[1];

        Registry r = null;
        try {
            r = LocateRegistry.createRegistry(port);
            Twitter t = new TwitterImpl() ;
            r.bind("twitter",t);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
        System.out.println("server Started.");
    }
}
