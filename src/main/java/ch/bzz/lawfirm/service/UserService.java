package ch.bzz.lawfirm.service;

import ch.bzz.lawfirm.data.UserData;
import ch.bzz.lawfirm.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * services for authentication and authorization of users
 */

@Path("user")
public class UserService {

    /**
     * login a user
     *
     * @param username the username
     * @param password the password
     * @return Response
     */
    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ){
        int httpStatus;
        User user = UserData.findUser(username, password);
        if (user == null || user.getRole() == null || user.getRole().equals("guest")){
            httpStatus = 401;
        } else {
            httpStatus = 200;
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                user.getRole(),
                "/",
                "", //z.B. ghwalin.ch
                "Login-Cookie",
                600,
                false
        );
        return Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
    }

    /**
     * logout current user
     *
     * @return Response object with guest-Cookie
     */
    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout(){
        NewCookie cookie = new NewCookie(
                "userRole",
                "guest",
                "/",
                "", //z.B. ghwalin.ch
                "Logout-Cookie",
                1,
                false
        );
        return Response
                .status(200)
                .entity("")
                .cookie(cookie)
                .build();
    }
}
