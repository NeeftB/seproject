package nl.pse.site.seproject.dao;

import nl.pse.site.seproject.dao.inter.IPhotoDAO;
import nl.pse.site.seproject.model.Photo;
import nl.pse.site.seproject.model.Report;
import nl.pse.site.seproject.rest.config.ApplicationConfig;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Transactional
@Named(ApplicationConfig.PHOTO_DAO_NAME)
public class PhotoDAO implements IPhotoDAO {

    @PersistenceContext(unitName = ApplicationConfig.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @Override
    public boolean addPhoto(Photo photo, Report report) {
        em.persist(photo);
        report.addPhoto(photo);
        em.flush();
        return true;
    }

    @Override
    public boolean updatePhoto(Photo photo, Photo newPhoto) {
        photo.setPath(newPhoto.getPath());
        photo.setMediaName(newPhoto.getMediaName());
        photo.setMediaType(newPhoto.getMediaType());
        em.flush();
        return true;
    }

    @Override
    public boolean deletePhoto(Photo photoToDelete) {
        return false;
    }

    @Override
    public boolean photoExist(int photoId) {
        Query query = em.createQuery("SELECT p from Photo p WHERE p.id = :photoId", Photo.class);
        query.setParameter("photoId", photoId);
        int count = query.getResultList().size();

        //implementatie van getId() van photo nog niet gemaakt.
        return true;
    }

    @Override
    public Photo getPhotoByReport(String reportNumber) {
        TypedQuery<Photo> query = em.createQuery("SELECT p from Photo p LEFT JOIN FETCH p.report r " +
                        "WHERE r.reportNumber = : reportNumber ",
                Photo.class);
        query.setParameter("reportNumber", reportNumber);
        return query.getSingleResult();
    }

    @Override
    public Photo getPhotoById(int photoId) {
        return null;
    }

    @Override
    public int getNumberOfPhoto(String reportNumber) {

        Query query = em.createQuery("SELECT p from Photo p LEFT JOIN FETCH p.report r " +
                        "WHERE r.reportNumber = : reportNumber ",
                Photo.class);
        query.setParameter("reportNumber", reportNumber);
        int count = query.getResultList().size();

        //kan nu nog enkel 1 foto worden toegevoegd. Zodra meerdere foto's dan deze functie afmaken.
        return 0;
    }
}
