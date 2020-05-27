package nl.pse.site.seproject.rest.service.inter;

import nl.pse.site.seproject.model.Photo;

import java.io.File;
import java.io.InputStream;
import java.util.Set;

public interface IPhotoService {

    boolean addPhoto(Photo photo, String reportNumber);
    boolean updatePhoto(Photo newphoto, String reportNumber);
    Set<Photo> getPhotosByReport(String reportNumber);
    String storeImage(InputStream inputStream, String reportNumber, String fileName);
    File getImage(String reportNumber);
    Photo getPhotoByReport(String reportNumber);
    Photo getPhotoById(int photoId);
    boolean deletePhoto(int photoId, String reportNumber);
    String updateImage(InputStream inputStream, String reportNumber, String fileName);
    boolean deleteAllImagesOfReport(String reportNumber);
}
