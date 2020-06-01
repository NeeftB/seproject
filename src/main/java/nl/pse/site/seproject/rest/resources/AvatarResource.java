package nl.pse.site.seproject.rest.resources;


import nl.pse.site.seproject.model.Avatar;
import nl.pse.site.seproject.rest.model.ClientApproval;
import nl.pse.site.seproject.rest.model.ClientError;
import nl.pse.site.seproject.rest.service.inter.IAvatarService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AvatarResource {

    private IAvatarService avatarService;

    public AvatarResource(IAvatarService avatarService) {
        this.avatarService = avatarService;
    }


    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvatarOfUser(@PathParam("username") String username){
        Avatar avatar = avatarService.getAvatar(username);
        if(avatar != null){
            return Response.status(Response.Status.OK).entity(avatar).build();
        } else {
            return Response.status(Response.Status.OK).entity(new ClientError("You do not have an avatar")).build();
        }
    }

    @POST
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAvatar(Avatar avatar, @PathParam("username") String username){
        if(avatarService.addAvatar(avatar, username)){
            return Response.status(Response.Status.CREATED).entity(new ClientApproval("Avatar added for "
                    + username)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ClientApproval("This request isn't valid "))
                    .build();
        }
    }

    @DELETE
    @Path("/{username}")
    public Response deleteAvatarFromUser(@PathParam("username") String username){
        return Response.status(Response.Status.OK).entity(avatarService.deleteAvatar(username)).build();
    }
}
