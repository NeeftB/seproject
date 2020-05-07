package nl.pse.site.seproject.rest.resources;

import nl.pse.site.seproject.model.Photo;
import nl.pse.site.seproject.model.Report;
import nl.pse.site.seproject.rest.model.ClientApproval;
import nl.pse.site.seproject.rest.model.ClientError;
import nl.pse.site.seproject.rest.service.inter.IPhotoService;
import nl.pse.site.seproject.rest.service.inter.IReportService;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

public class ReportResource {

    private IReportService reportService;
    private IPhotoService photoService;

    public ReportResource(IReportService reportService, IPhotoService photoService) {
        this.reportService = reportService;
        this.photoService = photoService;
    }

    @GET
    @Path("/{reportNumber}")
    @Produces
    public Response getReportByReportNumber(@PathParam("reportNumber")String reportNumber){
        return Response.status(Response.Status.OK).entity(reportService.getReportByReportNumber(reportNumber)).build();
    }

    @GET
    @Path("getallreports")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReports(){
        return Response.status(Response.Status.OK).entity(reportService.getAllReports()).build();
    }

    @GET
    @Path("/topten")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTopTenReports() {
        return Response.status(Response.Status.OK).entity(reportService.getTopTenReports()).build();
    }

    @GET
    @Path("/getreports/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReportsOfUser(@PathParam("username") String username) {
        return Response.status(Response.Status.OK).entity(reportService.getReportsOfUser(username)).build();
    }

    @PUT
    @Path("/addreport/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReport(@PathParam("username") String username, Report report){
        return Response.status(Response.Status.OK).entity(reportService.addReport(username, report)).build();
    }

    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReport(@FormDataParam("report-number") String reportNumber,
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

        Report updatedReport = new Report(title, country, region, category, paragraphOne, paragraphTwo, paragraphThree,
                published);

        if (!reportService.updateReport(updatedReport, reportNumber)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Report isn't added due an error"))
                    .build();
        }

        if (headImageContent != null) {
            String fileName = headImageContent.getFormDataContentDisposition().getFileName();
            String mediaType = headImageContent.getMediaType().toString();
            String path = photoService.updateImage(headImageInput, reportNumber, fileName);

            if (path == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Image isn't updated due an error"))
                        .build();
            }

            Photo photo = new Photo(path, mediaType, fileName);

            if (!photoService.updatePhoto(photo, reportNumber)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Photo isn't updated due an error "))
                        .build();
            } else {
                return Response.status(Response.Status.CREATED).entity(new ClientApproval("Report succesfully updated " +
                        reportNumber)).build();
            }

        } else {
            return Response.status(Response.Status.CREATED).entity(new ClientApproval("Report succesfully added " +
                    reportNumber + " without pictures")).build();
        }

    }

    @PUT
    @Path("publishreport/{reportNumber}")
    public Response publishReport(@PathParam("reportNumber") String reportNumber) {
        if(reportService.publishReport(reportNumber)){
            return Response.status(Response.Status.OK).entity(new ClientApproval("Report is published")).build();
        } else {
            return Response.status(Response.Status.OK).entity(new ClientApproval("Report couldn't be published")).build();
        }
    }
}
