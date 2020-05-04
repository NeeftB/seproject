package nl.pse.site.seproject.rest.resources;

import nl.pse.site.seproject.model.Photo;
import nl.pse.site.seproject.model.Report;
import nl.pse.site.seproject.rest.model.ClientApproval;
import nl.pse.site.seproject.rest.model.ClientError;
import nl.pse.site.seproject.rest.service.inter.IPhotoService;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.InputStream;

public class PhotoResource {

    private IPhotoService photoService;

    public PhotoResource(IPhotoService photoService) {
        this.photoService = photoService;
    }

    @GET
    @Path("/getphotos/{reportnumber}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getPhotosFromReport(@PathParam("reportnumber") String reportNumber){
        return Response.status(Response.Status.OK).entity(photoService.getImage(reportNumber))
                .header("Content-Disposition", "attachment; filename=photo1.jpg")
                .header("Content-Type", "application/octet-stream").build();
    }

}
