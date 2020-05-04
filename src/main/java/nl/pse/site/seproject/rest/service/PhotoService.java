package nl.pse.site.seproject.rest.service;

import nl.pse.site.seproject.dao.inter.IPhotoDAO;
import nl.pse.site.seproject.model.Photo;
import nl.pse.site.seproject.rest.config.ApplicationConfig;
import nl.pse.site.seproject.rest.service.inter.IPhotoService;
import nl.pse.site.seproject.rest.service.inter.IReportService;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.*;
import java.util.List;
import java.util.Set;

@Transactional
@Named(ApplicationConfig.PHOTO_SERVICE_NAME)
public class PhotoService implements IPhotoService {

    private final String PATHNAME = "C:\\Users\\neeftb\\HobbyBV\\TruthOrTravelBasic\\Backend\\uploads\\";

    private IPhotoDAO photoDAO;
    private IReportService reportService;

    @Inject
    public PhotoService(@Named(ApplicationConfig.PHOTO_DAO_NAME) IPhotoDAO photoDAO,
                        @Named(ApplicationConfig.REPORT_SERVICE_NAME) IReportService reportService) {
        this.photoDAO = photoDAO;
        this.reportService = reportService;
    }

    @Override
    public boolean addPhoto(Photo photo, String reportNumber) {
        if (getNumberOfPhoto(reportNumber) <= 5) {
            return photoDAO.addPhoto(photo, reportService.getReportByReportNumber(reportNumber));
        } else {
            return false;
        }
    }

    @Override
    public boolean updatePhoto(Photo newPhoto, String reportNumber) {
        if (photoDAO.photoExist(newPhoto.getId())) {
            return photoDAO.updatePhoto(photoDAO.getPhotoByReport(reportNumber), newPhoto);
        } else {
            return false;
        }
    }

    @Override
    public String storeImage(InputStream inputStream, String reportNumber, String fileName) {
        File file = createNewImageFile(reportNumber, fileName);
                if (file != null) {
                makeDirectory(file);
                return handleImageSave(inputStream, file);
            } else {
                return null;
            }

    }

    @Override
    public File getImage(String reportNumber) {
        return new File(getFilePath(reportNumber));

    }

    @Override
    public Set<Photo> getPhotosByReport(String reportNumber) {
        return reportService.getReportByReportNumber(reportNumber).getPhotos();
    }

    @Override
    public Photo getPhotoByReport(String reportNumber) {
        return photoDAO.getPhotoByReport(reportNumber);
    }

    @Override
    public Photo getPhotoById(int photoId) {
        return photoDAO.getPhotoById(photoId);
    }

    @Override
    public boolean deletePhoto(int photoId, String reportNumber) {
        if (getNumberOfPhoto(reportNumber) != 1 && photoDAO.photoExist(photoId)) {
            return photoDAO.deletePhoto(getPhotoById(photoId));
        } else {
            return false;
        }
    }

    @Override
    public String updateImage(InputStream inputStream, String reportNumber, String fileName) {
        deleteAllImagesInDirectory(reportNumber);
        File file = createNewImageFile(reportNumber, fileName);
        return handleImageSave(inputStream, file);

    }


    public int getNumberOfPhoto(String reportNumber) {
        return photoDAO.getNumberOfPhoto(reportNumber);
    }

    public File createNewImageFile(String reportNumber, String fileName) {
        try {
            return new File(PATHNAME +
                    reportNumber + "\\" + fileName);
        } catch (Exception e) {
            return null;
        }
    }

    private String handleImageSave(InputStream inputStream, File file) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(inputStream, outputStream);
            return file.getPath();
        } catch (FileNotFoundException e) {
            return null; //Some other actions must be implemented
        } catch (IOException e) {
            return null; //Some other actions must be implemented
        }
    }

    private void deleteAllImagesInDirectory(String reportNumber) {
        File directory = new File(PATHNAME +
                reportNumber);
        for (File d : directory.listFiles())
            if (!d.isDirectory())
                d.delete();
    }

    private void makeDirectory(File file) {
        file.getParentFile().mkdirs();
    }

    private String getFilePath(String reportNumber) {
        return getPhotoByReport(reportNumber).getPath();
    }

    public long getFileSize(InputStream inputStream) throws IOException {
        long sizeOfFile;
        File tmpFile = File.createTempFile("temp", null);

        try (OutputStream outputStream = new FileOutputStream(tmpFile)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            return 0; //Some other actions must be implemented
        } catch (IOException e) {
            return 0; //Some other actions must be implemented
        }

        sizeOfFile = tmpFile.length() / (1024 * 1024);
        tmpFile.delete();
        return sizeOfFile;
    }

}
