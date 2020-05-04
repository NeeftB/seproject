package nl.pse.site.seproject.dao.inter;

import nl.pse.site.seproject.model.Photo;
import nl.pse.site.seproject.model.Report;


public interface IPhotoDAO {

    boolean addPhoto(Photo photo, Report report);
    boolean updatePhoto(Photo photo, Photo newPhoto);
    boolean deletePhoto(Photo photoToDelete);
    boolean photoExist(int photoId);
    Photo getPhotoByReport(String reportNumber);
    Photo getPhotoById(int photoId);
    int getNumberOfPhoto(String reportNumber);
}

