import java.io.Serializable;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public class Hashtag implements Serializable{
    String name;
//    Destination destination;

    public Hashtag(String name/*, Destination destination*/) {
        this.name = name;
//        this.destination = destination;
//        System.out.println(destination);

    }

//    public Destination getDestination() {
//        return destination;
//    }

    public String getName() {
        return name;
    }
}
