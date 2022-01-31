import com.example.demo.Login;
import com.example.demo.LoginBean;
import com.example.demo.LoginResultBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLogin {

    @Test
    public void testLogin(){
        try{
            Login login = new Login();
            LoginBean bean = new LoginBean("alessio", "ciao");
            LoginResultBean resultBean=login.makeLogin(bean);
            assertEquals("alessio", resultBean.getUser());
        }
        catch(Exception e){
            assertEquals(1, 2);
        }


    }

}
