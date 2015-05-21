import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public class TwitterImpl extends UnicastRemoteObject implements Twitter {

    private List<String> hashtags;
    private List<User> users;

    public TwitterImpl() throws RemoteException {
        hashtags = new ArrayList<String>();
        users=new ArrayList<User>();
    }

    @Override
    public boolean newAccount(String username, String password) throws RemoteException {
        if (existsUser(username)) return false;

        users.add(new User(username, password));
        return true;
    }

    @Override
    public User connect(String username, String password) throws RemoteException {
        return (loggedUser(username, password));
    }

    @Override
    public boolean createHashtag(String hashtag) throws RemoteException {
        if (existsHashtag(hashtag)) return false;

        hashtags.add(hashtag);
        return true;
    }

    private boolean existsUser(String username){
        for (User u : users)
            if (u.getUsername().equals(username))
                return true;
        return false;
    }

    private User loggedUser(String username, String password) {
        for (User u : users)
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return u;
        return null;
    }

    private boolean existsHashtag(String hashtag){
        for (String s : hashtags)
            if (s.equals(hashtag))
                return true;
        return false;
    }



    @Override
    public String toString(){
        return "Je suis un twitter de "+users.size()+" users et "+hashtags.size()+" hastags.";
    }
}
