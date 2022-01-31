import com.example.demo.MakeReview;
import com.example.demo.Review;
import com.example.demo.User;
import com.example.demo.Videogame;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestAverageScore {

    @Test
    public void testUpdScore(){
        Videogame v;
        v = new Videogame("a game", -1, "a genre", 2000);
        User u1 = new User("user1", "pass1", "email1", false);
        User u2 = new User("user2", "pass2", "email2", false);
        User u3 = new User("user3", "pass3", "email3", false);
        User u4 = new User("user4", "pass4", "email4", false);
        User u5 = new User("user5", "pass5", "email5", false);
        List<Review> list = new ArrayList<>();
        list.add (new Review(v, u1, "a text", 8, new Date()));
        list.add (new Review(v, u2, "a text", 6, new Date()));
        list.add (new Review(v, u3, "a text", 2, new Date()));
        list.add (new Review(v, u4, "a text", 4, new Date()));
        list.add (new Review(v, u5, "a text", 7, new Date()));
        MakeReview make = new MakeReview();
        int avg=make.averageScore(v, list);
        assertEquals(5, avg);
        list = new ArrayList<>();
        avg=make.averageScore(v, list);
        assertEquals(-1, avg);
    }

}