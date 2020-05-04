package nl.pse.site.seproject.dao.inter;

import nl.pse.site.seproject.model.Avatar;
import nl.pse.site.seproject.model.User;

public interface IAvatarDAO {

    boolean addAvatar(Avatar avatar, User user);
    boolean deleteAvatar(Avatar avatar, User user);
}
