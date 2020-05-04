package nl.pse.site.seproject.rest.resources;

import nl.pse.site.seproject.model.Photo;
import nl.pse.site.seproject.model.Report;
import nl.pse.site.seproject.model.User;
import nl.pse.site.seproject.rest.jwt.filter.Secured;
import nl.pse.site.seproject.rest.model.ClientApproval;
import nl.pse.site.seproject.rest.model.ClientError;
import nl.pse.site.seproject.rest.service.inter.ICountryService;
import nl.pse.site.seproject.rest.service.inter.IPhotoService;
import nl.pse.site.seproject.rest.service.inter.IUserService;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.InputStream;
import java.security.Principal;

public class UserResource {

    private IUserService userService;
    private ICountryService countryService;
    private IPhotoService photoService;

    public UserResource(IUserService userService, ICountryService countryService, IPhotoService photoService) {
        this.userService = userService;
        this.countryService = countryService;
        this.photoService = photoService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        return Response.status(Response.Status.OK).entity(userService.getAllUsers()).build();
    }

    @GET
    @Secured
    @PermitAll
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUsername(@PathParam("username") String username
//            ,                                      @Context SecurityContext securityContext
    ) {
//        Principal principal = securityContext.getUserPrincipal();
//        String testName = principal.getName();
        User user = userService.getUserByName(username);

        if (user != null) {
            return Response.status(Response.Status.OK).entity(userService.getUserByName(username))
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Username doesn't exist"))
                    .build();
        }
    }

    @GET
    @Path("/{username}/countries")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHolidaysFromUser(@PathParam("username") String username) {
        return Response.status(Response.Status.OK).entity(countryService.getAllHolidaysFromUser(username)).build();
    }

    @GET
    @Path("/{username}/countries/current")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentResidentFromUser(@PathParam("username") String username) {
        return Response.status(Response.Status.OK).entity(countryService.getCurrentResident(username)).build();
    }

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        if (userService.addUser(user)) {
            return Response.status(Response.Status.CREATED).entity(new ClientApproval("User added succesfully"))
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Username is already in use"))
                    .build();
        }
    }

    @POST
    @Path("/{username}/countries/{countryName}")
    public Response addHolidayToUser(@PathParam("username") String username,
                                     @PathParam("countryName") String countryName) {
        if (userService.addHolidayCountryToUser(username, countryName)) {
            return Response.status(Response.Status.CREATED).entity(new ClientApproval("Holiday added for "
                    + username)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ClientApproval("This request isn't valid "))
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}/report")
    public Response addReport(@PathParam("username") String username,
                         @FormDataParam("title") String title,
                         @FormDataParam("country") String country,
                         @FormDataParam("region") String region,
                         @FormDataParam("category") String category,
                         @FormDataParam("paragraph-one") String paragraphOne,
                         @FormDataParam("paragraph-two") String paragraphTwo,
                         @FormDataParam("paragraph-three") String paragraphThree,
                         @FormDataParam("published") Boolean published,
                         @FormDataParam("head-image") FormDataBodyPart headImageContent,
                         @FormDataParam("head-image") InputStream headImageInput) throws Exception {


        Report report = new Report(title, country, region, category, paragraphOne, paragraphTwo, paragraphThree,
                published);

        if (!userService.addReportToUser(username, report)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Report isn't added due an error"))
                    .build();
        }

        if (headImageContent != null) {
            String fileName = headImageContent.getFormDataContentDisposition().getFileName();
            String mediaType = headImageContent.getMediaType().toString();
            String path;

            path = photoService.storeImage(headImageInput, report.getReportNumber(), fileName);

            if (path == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Image isn't saved due an error"))
                        .build();
            }

            Photo photo = new Photo(path, mediaType, fileName);

            if (!photoService.addPhoto(photo, report.getReportNumber())) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Photo isn't added due an error "))
                        .build();
            } else {
                return Response.status(Response.Status.CREATED).entity(new ClientApproval("Report succesfully added " +
                        report.getReportNumber())).build();
            }

        } else {
            return Response.status(Response.Status.CREATED).entity(new ClientApproval("Report succesfully added " +
                    report.getReportNumber() + " without pictures")).build();
        }
    }

    @DELETE
    @Path("/{username}/reports/{reportNumber}")
    public Response removeReportFromUser(@PathParam("username") String username,
                                         @PathParam("reportNumber") String reportNumber) {
        if (userService.removeReportFromUser(username, reportNumber)) {
            return Response.status(Response.Status.OK).entity(new ClientApproval("Report deleted")).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Report not deleted during error")).build();
        }
    }


    @DELETE
    @Path("/{username}/countries/{countryName}")
    public Response deleteHolidayFromUser(@PathParam("username") String username,
                                          @PathParam("countryName") String countryName) {
        if (userService.removeHolidayFromUser(username, countryName)) {
            return Response.status(Response.Status.OK).entity(new ClientApproval("Holiday deleted")).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Holiday not deleted during error")).build();
        }
    }

    @DELETE
    @Path("/{username}")
    public Response deleteUser(@PathParam("username") String username) {
        if (userService.deleteUser(username)) {
            return Response.status(Response.Status.OK).entity(new ClientApproval("User deleted")).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("User not deleted during error")).build();
        }
    }
}
