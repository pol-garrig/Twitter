import java.io.Serializable;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public class User implements Serializable {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Twittos : "+username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
