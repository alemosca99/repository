import com.example.demo.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestInsertVideogame {

    @Test
    public void testInsertVideogame(){
        InsertGame insert = new InsertGame();
        InsertGameBean bean = new InsertGameBean("example", "a genre", 2018);
        ViewGames view = new ViewGames();
        List<ViewGamesBean> list;
        String check="";
        //prova inserimento
        try {
            insert.insertGame(bean);
        }
        catch(DuplicatedInstanceException | SQLException | ClassNotFoundException e){
            assertEquals(1, -1);
        }
        //controllo presenza gioco
        try{
            list=view.viewGames();
            for(ViewGamesBean b : list){
                if(b.getName().equals("example")){
                    check="ok";
                    assertEquals(-1, b.getScore());
                    assertEquals("a genre", b.getGenre());
                    assertEquals(2018, b.getYear());
                }
            }
        }
        catch(ItemNotFoundException | SQLException | ClassNotFoundException e){
            assertEquals(1, -1);
        }
        assertEquals(check, "ok");
        //cancellazione gioco
        try{
            DAOVideogame.getDAOVideogameInstance().deleteVideogame("example");
        }
        catch(ClassNotFoundException | SQLException e){
            assertEquals(1, -1);
        }
    }
}
