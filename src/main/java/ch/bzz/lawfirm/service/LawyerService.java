package ch.bzz.lawfirm.service;

import ch.bzz.lawfirm.model.Lawyer;
import ch.bzz.lawfirm.data.DataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * lawyer service
 */

@Path("lawyer")
public class LawyerService {

    /**
     * lists all lawyers
     *
     * @return lawyerList
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listLawyers(){
        List<Lawyer> lawyerList = DataHandler.getInstance().readAllLawyers();
        return Response
                .status(200)
                .entity(lawyerList)
                .build();
    }

    /**
     * reads a lawyer by its id
     *
     * @param lawyerID
     * @return a lawyer
     */

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readLawyer(
            @QueryParam("id") int lawyerID
    ){
        Lawyer lawyer = DataHandler.getInstance().readLawyerByID(lawyerID);
        return Response
                .status(200)
                .entity(lawyer)
                .build();
    }
}
