import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public interface Twitter extends Remote {

    boolean newAccount(String username, String password) throws RemoteException;

    User connect(String username, String password) throws RemoteException;

    boolean createHashtag(String hashtag ) throws RemoteException;

}
