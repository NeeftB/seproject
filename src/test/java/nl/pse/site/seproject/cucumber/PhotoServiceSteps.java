package nl.pse.site.seproject.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.pse.site.seproject.dao.PhotoDAO;
import nl.pse.site.seproject.rest.resources.PhotoResource;
import nl.pse.site.seproject.rest.service.PhotoService;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import java.io.IOException;
import java.io.InputStream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class PhotoServiceSteps {

    PhotoResource photoResource = mock(PhotoResource.class);
    PhotoService photoService = mock(PhotoService.class);
    PhotoDAO photoDAO = mock(PhotoDAO.class);
    InputStream is = mock(InputStream.class);
    FormDataBodyPart fd = mock(FormDataBodyPart.class);


    @Given("that user Maria uploaded a new photo for her blog.")
    public void that_user_Maria_uploaded_a_new_photo_for_her_blog() throws Exception {
        photoResource.addPhotoToReport(anyString(), eq(fd), eq(is));
    }

    @When("Maria uploaded a photo bigger than 3MB and the frontend did not checked this correctly")
    public void maria_uploaded_a_photo_bigger_than_3MB_and_the_frontend_did_not_checked_this_correctly()throws IOException{
        given(photoService.getFileSize(is)).willReturn((long) 4);
    }

    @Then("the backend system must denied the upload action")
    public void the_backend_system_must_denied_the_action() throws IOException {
        given(photoService.getFileSize(is)).willReturn((long) 4);
        assertEquals( null, photoService.storeImage(eq(is), anyString(), anyString()));
    }


    @Given("that user Tino want to delete a photo.")
    public void that_user_Tino_has_pushed_the_delete_button_to_delete_the_photo() {
        photoService.deletePhoto(anyInt(), anyString());
    }

    @When("Tino only has got one photo in his blog and the frontend did not checked this correctly")
    public void tino_only_has_got_one_photo_in_his_blog_and_the_frontend_did_not_checked_this_correctly() {
        given(photoDAO.getNumberOfPhoto(anyString())).willReturn(1);
    }

    @Then("the backend system must denied the delete action")
    public void the_system_must_denied_the_action() {
        assertFalse(photoService.deletePhoto(anyInt(), anyString()));
    }
}
