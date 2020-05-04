package nl.pse.site.seproject.rest.resources;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.pse.site.seproject.model.User;
import nl.pse.site.seproject.rest.jwt.JwtUtils;
import nl.pse.site.seproject.rest.model.ClientError;
import nl.pse.site.seproject.rest.service.inter.IUserService;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.security.Key;
import java.util.Date;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

public class AuthenticationResource {

    private IUserService userService;

    public AuthenticationResource(IUserService userService) {
        this.userService = userService;
    }

    @POST
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User user, @Context UriInfo uri) {
        String username = user.getUsername();
        String password = user.getPassword();

        try {

            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username, uri);

            // Return the token on the response
            return Response.ok().header(AUTHORIZATION, token).
                    entity(userService.getUserByName(username)).build();

        } catch (IllegalAccessException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(new ClientError("Credentials not correct")).build();
        }
    }

    private void authenticate(String username, String password) throws IllegalAccessException {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
            if (!userService.checkPassword(username, password)) {
                throw new IllegalAccessException();
            }

    }

    private String issueToken(String username, UriInfo uri) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        Key key = JwtUtils.getKey();

        // Could have more than one role, but now it is just one
        String role = userService.getRole(username);

        String jwtToken = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuer(uri.getPath())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 15 minutes
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return jwtToken;
    }
}



