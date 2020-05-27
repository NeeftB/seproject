package nl.pse.site.seproject.rest.config;

import nl.pse.site.seproject.config.CorsFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Configuration for Rest Endpoint using JAX-RS.
 * Here the application path (start url) is defined.
 */
@ApplicationPath("services")
public class ApplicationConfig extends ResourceConfig {

    public final static String PERSISTENCE_UNIT_NAME = "SEP_PU";

    public final static String USER_DAO_NAME = "user_dao";
    public final static String AVATAR_DAO_NAME = "avatar_dao";
    public final static String COUNTRY_DAO_NAME = "country_dao";
    public final static String PHOTO_DAO_NAME = "photo_dao";
    public final static String REPORT_DAO_NAME = "report_dao";

    public final static String USER_SERVICE_NAME = "user_service";
    public final static String AVATAR_SERVICE_NAME = "avatar_service";
    public final static String COUNTRY_SERVICE_NAME = "country_service";
    public final static String PHOTO_SERVICE_NAME = "photo_service";
    public final static String REPORT_SERVICE_NAME = "report_service";

    public ApplicationConfig(){
        //To which packages the application must scan for
        //the REST request source
        packages("nl.pse.site.seproject.rest.controller");

        //Register MultiPartFeature to implement classes for
        //multipart/formdata requests.
        register(MultiPartFeature.class);

        //If the Provider annotation in the CorsFilter class doesn't work
        //this will register the CorsFilter for you.
        register(new CorsFilter());
    }
}
