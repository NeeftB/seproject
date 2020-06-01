package nl.pse.site.seproject.rest.resources;

import nl.pse.site.seproject.model.Country;
import nl.pse.site.seproject.rest.model.ClientError;
import nl.pse.site.seproject.rest.service.inter.ICountryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CountryResource {

    private ICountryService countryService;

    public CountryResource(ICountryService countryService) {
        this.countryService = countryService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCountries(){
        return Response.status(Response.Status.OK).entity(countryService.getAllCountries()).build();
    }

    @GET
    @Path("/{countryName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountryByName(@PathParam("countryName") String countryName){
        if(countryService.getCountryByName(countryName) != null){
            return Response.status(Response.Status.OK).entity(countryService.getCountryByName(countryName))
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ClientError("Country doesn't exist"))
                    .build();
        }
    }

    @GET
    @Path("/continent/{continentName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountriesByContinent(@PathParam("continentName") String continentName){
        return Response.status(Response.Status.OK).entity(countryService.getCountriesByContinent(continentName)).build();
    }

    @GET
    @Path("/language/{language}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountriesByLanguage(@PathParam("language") String language){
        return Response.status(Response.Status.OK).entity(countryService.getCountriesByLanguage(language)).build();
    }

    @PUT
    @Path("/{countryName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCountry(Country updatedCountry){
        return Response.status(Response.Status.OK).entity(countryService.updateCountry(updatedCountry)).build();
    }

    @DELETE
    @Path("/{countryName}")
    public Response deleteCountry(@PathParam("countryName") String countryName){
        return Response.status(Response.Status.OK).entity(countryService.deleteCountry(countryName)).build();
    }
}
