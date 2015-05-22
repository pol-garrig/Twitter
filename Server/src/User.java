import javax.jms.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public class User implements Serializable ,MessageListener {

    private String username;
    private String password;
    private List<Hashtag> followedHashtags;
    private List<User> followedUsers;

    public User(String username, String password, Session session) {
        this.username = username;
        this.password = password;
        this.followedHashtags=new ArrayList<>();
        this.followedUsers=new ArrayList<>();
    }

    public boolean isFollowingHashtag(String hashtag){
        return getHashtag(hashtag)!=null;
    }

    private Hashtag getHashtag(String hashtag){
        for (Hashtag h : followedHashtags)
            if (h.getName().equals(hashtag))
                return h;
        return null;

    }

    private void followHastag(Hashtag hashtag){
        if (!isFollowingHashtag(hashtag.getName()))
            this.followedHashtags.add(hashtag);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Hashtag> getHashtags() {
        return followedHashtags;
    }



    @Override
    public void onMessage(Message message) {
        MapMessage mm = (MapMessage)message;
        try {
            System.out.println("New tweet :\n\t"+mm.getString("Author")+" -> "+ mm.getString("Content"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String twittos = "Twittos : "+username+"\nFollowing hashtags :+\n";
        for (Hashtag h : followedHashtags)
            twittos+="\t-"+h.getName()+"\n";
        return twittos;
    }

}
