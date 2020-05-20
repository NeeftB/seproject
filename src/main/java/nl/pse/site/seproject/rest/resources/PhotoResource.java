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
    public Response getPhotosFromReport(@PathParam("reportnumber") String reportNumber) {
        return Response.status(Response.Status.OK).entity(photoService.getImage(reportNumber))
                .header("Content-Disposition", "attachment; filename=photo1.jpg")
                .header("Content-Type", "application/octet-stream").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response addPhotoToReport(@FormDataParam("reportNumber") String reportNumber,
                                     @FormDataParam("new-image") FormDataBodyPart headImageContent,
                                     @FormDataParam("new-image") InputStream headImageInput) throws Exception {
        if (headImageContent != null) {
            String fileName = headImageContent.getFormDataContentDisposition().getFileName();
            String mediaType = headImageContent.getMediaType().toString();
            String path;

            path = photoService.storeImage(headImageInput, reportNumber, fileName);

            if (path == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Image isn't saved due an error"))
                        .build();
            }

            Photo photo = new Photo(path, mediaType, fileName);

            if (!photoService.addPhoto(photo, reportNumber)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Photo isn't added due an error "))
                        .build();
            } else {
                return Response.status(Response.Status.CREATED).entity(new ClientApproval("Photo succesfully added ")).build();
            }
        } else {
            return Response.status(Response.Status.CONFLICT).entity(new ClientError("Photo could not be added")).build();
        }

    }

}
