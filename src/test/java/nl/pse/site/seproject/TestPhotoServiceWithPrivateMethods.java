package nl.pse.site.seproject;

import nl.pse.site.seproject.dao.PhotoDAO;
import nl.pse.site.seproject.rest.service.PhotoService;
import nl.pse.site.seproject.rest.service.ReportService;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PhotoService.class)
public class TestPhotoServiceWithPrivateMethods {

    @InjectMocks
    PhotoService photoService;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Mock
    PhotoDAO photoDAO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sizeBiggerThanThreeMbIsNotAllowed() throws Exception {
        PowerMockito.doReturn(folder.newFile()).when(photoService, "storeImage");
        PowerMockito.doReturn(4).when(photoService, "getFileSize");
        InputStream stubInputStream = IOUtils.toInputStream("test data", "UTF-8");


        PowerMockito.verifyPrivate(photoService).invoke("storeImage");
        assertEquals(null, photoService.storeImage(stubInputStream, anyString(), anyString()));
    }
//
//    @Override
//    public String storeImage(InputStream inputStream, String reportNumber, String fileName) {
//        File file = createNewImageFile(reportNumber, fileName);
//        if(getFileSize(file) < 3){
//            makeDirectory(file);
//            return handleImageSave(inputStream, file);
//        } else {
//            return null;
//        }
//    }

}
