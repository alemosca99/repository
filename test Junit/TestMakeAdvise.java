import com.example.demo.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMakeAdvise {

    @Test
    public void testMakeAdvise(){
        MakeAdvise make = new MakeAdvise();
        MakeAdviseBean bean = new MakeAdviseBean("alessio", "an example");
        ViewAdvice view = new ViewAdvice();
        List<ViewAdviceBean> list;
        String check="";
        int id=0;
        //creazione consiglio
        try{
            make.makeAdvise(bean);
        }
        catch( SQLException | ClassNotFoundException e){
            assertEquals(1, -1);
        }
        //controllo esistenza consiglio
        try{
            list=view.viewAllAdvice();
            for(ViewAdviceBean b : list){
                if(b.getUser().equals("alessio") && b.getText().equals("an example")){
                   check="ok";
                   id=b.getId();
                }
            }
        }
        catch( ItemNotFoundException | SQLException | ClassNotFoundException e){
            assertEquals(1, -1);
        }
        assertEquals("ok", check);
        //cancellazione consiglio
        try{
            DAOAdvise.getDAOAdviseInstance().deleteAdvise("alessio", id);
        }
        catch( SQLException | ClassNotFoundException e){
            assertEquals(1, -1);
        }
    }


}
