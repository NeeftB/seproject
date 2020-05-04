package nl.pse.site.seproject.dao;


import nl.pse.site.seproject.dao.inter.IAvatarDAO;
import nl.pse.site.seproject.model.Avatar;
import nl.pse.site.seproject.model.User;
import nl.pse.site.seproject.rest.config.ApplicationConfig;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
@Transactional
@Named(ApplicationConfig.AVATAR_DAO_NAME)
public class AvatarDAO implements IAvatarDAO {

    @PersistenceContext(unitName = ApplicationConfig.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @Override
    public boolean addAvatar(Avatar avatar, User user) {
        em.persist(avatar);
        user.setAvatar(avatar);
        em.flush();
        return true;
    }

    @Override
    public boolean deleteAvatar(Avatar avatar, User user) {
        em.remove(avatar);
        user.setAvatar(null);
        return false;
    }
}
