package datas;

import java.io.Serializable;

/**
 * Created by Tom Veniat on 21/05/15.
 */
public class Hashtag implements Serializable{
    String name;

    public Hashtag(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}