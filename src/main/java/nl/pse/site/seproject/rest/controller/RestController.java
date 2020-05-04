package nl.pse.site.seproject.rest.controller;

import nl.pse.site.seproject.rest.config.ApplicationConfig;
import nl.pse.site.seproject.rest.resources.*;
import nl.pse.site.seproject.rest.service.inter.*;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;

@Path("rest")
public class RestController {

    private IUserService userService;
    private IAvatarService avatarService;
    private ICountryService countryService;
    private IPhotoService photoService;
    private IReportService reportService;

    @Inject
    public RestController(
            @Named(ApplicationConfig.USER_SERVICE_NAME)IUserService userService,
            @Named(ApplicationConfig.AVATAR_SERVICE_NAME)IAvatarService avatarService,
            @Named(ApplicationConfig.COUNTRY_SERVICE_NAME)ICountryService countryService,
            @Named(ApplicationConfig.PHOTO_SERVICE_NAME) IPhotoService photoService,
            @Named(ApplicationConfig.REPORT_SERVICE_NAME) IReportService reportService
            ){
        this.userService = userService;
        this.avatarService = avatarService;
        this.countryService = countryService;
        this.photoService = photoService;
        this.reportService = reportService;
    }

    @Path("/avatars")
    public AvatarResource getAvatarResource(){
        return new AvatarResource(avatarService);
    }

    @Path("/countries")
    public CountryResource getCountryResource(){
        return new CountryResource(countryService);
    }

    @Path("/photos")
    public PhotoResource getPhotoResource(){
        return new PhotoResource(photoService);
    }

    @Path("/reports")
    public ReportResource getReportResource(){
        return new ReportResource(reportService, photoService);
    }

    @Path("/users")
    public UserResource getUserResource(){
        return new UserResource(userService, countryService, photoService);
    }

    @Path("/authentication")
    public AuthenticationResource getAuthenticationResource(){
        return new AuthenticationResource(userService);
    }

}
