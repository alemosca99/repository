import com.example.demo.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestAdviseResult {

    @Test
    public void testAdviseResult(){
        //dichiarazione utenti
        User u1 = new User("user1", "pass1", "email1", false);
        User u2 = new User("user2", "pass2", "email2", false);
        User u3 = new User("user3", "pass3", "email3", false);
        User u4 = new User("user4", "pass4", "email4", false);
        User u5 = new User("user5", "pass5", "email5", false);
        //dichiarazione videogiochi
        Videogame v1 = new Videogame("game1", -1, "genre1", 2000);
        Videogame v2 = new Videogame("game2", -1, "genre2", 2000);
        Videogame v3 = new Videogame("game3", -1, "genre3", 2000);
        //creazione consiglio e risultati
        Advise a = new Advise(1, u1, "a text");
        a.getResult().add(new AdviseResult(u1, 1, u2, v1));
        a.getResult().add(new AdviseResult(u1, 1, u3, v2));
        a.getResult().add(new AdviseResult(u1, 1, u4, v3));
        a.getResult().add(new AdviseResult(u1, 1, u5, v3));
        //operazione risultati
        ViewAdvice view = new ViewAdvice();
        List<ViewAdviseResultBean> result=view.calculateResult(a);
        //controlla risultati
        for(ViewAdviseResultBean bean : result){
            if(bean.getVideogame().equals("game1")){
                assertEquals(1, bean.getAdvice());
            }
            if(bean.getVideogame().equals("game2")){
                assertEquals(1, bean.getAdvice());
            }
            if(bean.getVideogame().equals("game3")){
                assertEquals(2, bean.getAdvice());
            }
        }
    }

}
