import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public interface Twitter extends Remote {

    boolean newAccount(String username, String password) throws RemoteException;

    User connect(String username, String password) throws RemoteException;

    boolean createHashtag(String hashtag ) throws RemoteException;

    boolean followHashtag(String username, String hashtag ) throws RemoteException;

    List<User> allUsers() throws RemoteException;

    List<Hashtag> allHashtags() throws RemoteException;

//    boolean postMessage(User user, String message, String hashtag ) throws RemoteException;


}
