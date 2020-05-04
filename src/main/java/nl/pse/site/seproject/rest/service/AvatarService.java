package nl.pse.site.seproject.rest.service;

import nl.pse.site.seproject.dao.inter.IAvatarDAO;
import nl.pse.site.seproject.model.Avatar;
import nl.pse.site.seproject.rest.config.ApplicationConfig;
import nl.pse.site.seproject.rest.service.inter.IAvatarService;
import nl.pse.site.seproject.rest.service.inter.IUserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Transactional
@Named(ApplicationConfig.AVATAR_SERVICE_NAME)
public class AvatarService implements IAvatarService {

    private IAvatarDAO avatarDAO;
    private IUserService userService;

    @Inject
    public AvatarService(
            @Named(ApplicationConfig.AVATAR_DAO_NAME)IAvatarDAO avatarDAO,
            @Named(ApplicationConfig.USER_SERVICE_NAME) IUserService userService){
        this.avatarDAO = avatarDAO;
        this.userService = userService;
    }

    @Override
    public Avatar getAvatar(String username) {
        return userService.getUserByName(username).getAvatar();
    }

    @Override
    public boolean addAvatar(Avatar avatar, String username) {
        if(userService.userExists(username)){
            return avatarDAO.addAvatar(avatar,userService.getUserByName(username));
        } else{
            return false;
        }

    }

    @Override
    public boolean deleteAvatar(String username) {
        return avatarDAO.deleteAvatar(getAvatar(username), userService.getUserByName(username));
    }
}
