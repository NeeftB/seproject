package nl.pse.site.seproject;

import nl.pse.site.seproject.dao.PhotoDAO;
import nl.pse.site.seproject.model.Photo;
import nl.pse.site.seproject.model.Report;
import nl.pse.site.seproject.rest.service.PhotoService;
import nl.pse.site.seproject.rest.service.ReportService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class TestPhotoService {

    @InjectMocks
    PhotoService photoService;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Mock
    PhotoDAO photoDAO;

    @Mock
    ReportService reportService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void moreThenFivePhotosIsNotAllowed(){
        Photo photo = new Photo();
        when(photoDAO.getNumberOfPhoto(anyString())).thenReturn(6);

        assertFalse(photoService.addPhoto(photo, "number"));
    }

    @Test
    public void lessThenSixPhotosIsAllowed(){
        Photo photo = new Photo();
        when(reportService.getReportByReportNumber(anyString())).thenReturn(new Report());
        Report report = reportService.getReportByReportNumber(anyString());
        when(photoDAO.addPhoto(photo, report)).thenReturn(true);
        when(photoDAO.getNumberOfPhoto(anyString())).thenReturn(4);

        assertTrue(photoService.addPhoto(photo, "number"));
    }

    @Test
    public void deleteLastPhotoNotAllowed() {
        when(photoDAO.getNumberOfPhoto(anyString())).thenReturn(1);

        assertFalse(photoService.deletePhoto(4, anyString()));
    }

    @Test
    public void deletePhotoAllowed() {
        when(photoDAO.getPhotoById(anyInt())).thenReturn(new Photo());
        Photo photoToDelete = photoDAO.getPhotoById(anyInt());
        when(photoDAO.getNumberOfPhoto(anyString())).thenReturn(2);
        when(photoDAO.photoExist(anyInt())).thenReturn(true);
        when(photoDAO.deletePhoto(photoToDelete)).thenReturn(true);

        assertTrue(photoService.deletePhoto(4, anyString()));
    }

    @Test
    public void updatePhotoButItDoesNotExist(){
        when(photoDAO.photoExist(anyInt())).thenReturn(false);

        assertFalse(photoService.updatePhoto(new Photo(), anyString()));
    }

    @Test
    public void updatePhotoWhenExist() {
        Photo newPhoto = new Photo();
        when(photoDAO.photoExist(anyInt())).thenReturn(true);
        when(photoDAO.getPhotoByReport(anyString())).thenReturn(new Photo());

        Photo photo = photoDAO.getPhotoByReport(anyString());
        when(photoDAO.updatePhoto(photo, newPhoto)).thenReturn(true);

        assertTrue(photoService.updatePhoto(newPhoto, anyString()));
    }

}
