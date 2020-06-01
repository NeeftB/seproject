package nl.pse.site.seproject;

import nl.pse.site.seproject.dao.AvatarDAO;
import nl.pse.site.seproject.model.Avatar;
import nl.pse.site.seproject.model.User;
import nl.pse.site.seproject.rest.service.AvatarService;
import nl.pse.site.seproject.rest.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class TestAvatarService {


    @InjectMocks
    AvatarService avatarService;

    @Mock
    AvatarDAO avatarDAO;

    @Mock
    UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void userIsNotKnownSoIsNotAllowed() {
        Avatar avatar = new Avatar();
        when(userService.userExists(anyString())).thenReturn(false);

        assertFalse(avatarService.addAvatar(avatar, anyString()));
    }

    @Test
    public void userIsKnownSoAvatarCanBeAdded() {
        Avatar avatar = new Avatar();
        when(userService.userExists(anyString())).thenReturn(true);

        when(userService.getUserByName(anyString())).thenReturn(new User());
        User user = userService.getUserByName(anyString());

        when(avatarDAO.addAvatar(avatar, user)).thenReturn(true);

        assertTrue(avatarService.addAvatar(avatar, anyString()));
    }

}
