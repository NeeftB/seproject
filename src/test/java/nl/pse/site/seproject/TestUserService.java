package nl.pse.site.seproject;


import nl.pse.site.seproject.dao.UserDAO;
import nl.pse.site.seproject.model.User;
import nl.pse.site.seproject.rest.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class TestUserService {

    @InjectMocks
    UserService userService;

    @Mock
    UserDAO userDAO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doubleUsernameNotAllowed() {
        User user = new User("test", "12345", "test@test.nl", "test", "",
                "test", 18, "Male", "", 0);
        when(userDAO.userExists(anyString())).thenReturn(true);

        assertFalse(userService.addUser(user));
    }

    @Test
    public void emailAddressAlreadyGotAnAccount(){
        User user = new User("test", "12345", "test@test.nl", "test", "",
                "test", 18, "Male", "", 0);
        when(userDAO.emailAlreadyInUse(anyString())).thenReturn(true);

        assertFalse(userService.addUser(user));
    }

    @Test
    public void addUserWithSuccess(){
        User user = new User("test", "12345", "test@test.nl", "test", "",
                "test", 18, "Male", "", 0);
        when(userDAO.userExists(anyString())).thenReturn(false);
        when(userDAO.emailAlreadyInUse(anyString())).thenReturn(false);
        when(userDAO.addUser(user)).thenReturn(true);

//        assertTrue(userService.addUser(user));
        fail();

    }

}
