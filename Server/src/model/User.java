package model;

import javax.jms.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public class User implements Serializable {


    private String username;
    private String password;
    private List<Hashtag> followedHashtags;
    private List<User> followedUsers;

    public User(String username, String password, Session session) {
        this.username = username;
        this.password = password;
        this.followedHashtags=new ArrayList<>();
        this.followedUsers=new ArrayList<>();
        this.followedUsers.add(this);
    }

    private boolean isFollowingHashtag(String hashtag){
        return getHashtag(hashtag)!=null;
    }

    private Hashtag getHashtag(String hashtag){
        for (Hashtag h : followedHashtags)
            if (h.getName().equals(hashtag))
                return h;
        return null;
    }

    public void followHastag(Hashtag hashtag){
        if (!isFollowingHashtag(hashtag.getName())){
            this.followedHashtags.add(hashtag);
            System.out.println("hashtag added");
        }else System.out.println("hashtag not added");
    }

    private boolean isFollowingUser(String user){
        System.out.println("\tisFollowingUser call answer : "+getUser(user)!=null);
        return getUser(user)!=null;
    }

    private User getUser(String user){
        System.out.println("SEARCHING : "+user);
        for (User u : followedUsers)
            if (u.getUsername().equals(user)){
                System.out.println("\tgetUser call answer : "+u);
                return u;
            }
        System.out.println("\ton a pas trouvé "+user );
        return null;

    }

    public void followUser(User user){
        System.out.println("follow user :");
        if (!isFollowingUser(user.getUsername())){
            this.followedUsers.add(user);
            System.out.println("user added");
        }else System.out.println("user already added");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Hashtag> getFollowedHashtags() {
        return followedHashtags;
    }

    public List<User> getFollowedUsers() {
        return followedUsers;
    }

    @Override
    public String toString() {
        String twittos = "Twittoseur : "+username+" who follows :\n";
        for (User u : followedUsers)
            twittos+="\t@"+u.getUsername()+"\n";
        for (Hashtag h : followedHashtags)
            twittos+="\t#"+h.getName()+"\n";
        return twittos;
    }

}