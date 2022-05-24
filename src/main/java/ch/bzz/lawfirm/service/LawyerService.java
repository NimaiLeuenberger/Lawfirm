package ch.bzz.lawfirm.service;

import ch.bzz.lawfirm.model.Lawyer;
import ch.bzz.lawfirm.data.DataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * lawyer service
 */

@Path("lawyer")
public class LawyerService{

    /**
     * lists all lawyers
     *
     * @param sort
     * @return lawyerList
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listLawyers (
            @QueryParam("sort") boolean sort
    ){
        List<Lawyer> lawyerList = DataHandler.getInstance().readAllLawyers();
        if (sort) {
            Collections.sort(lawyerList, new Comparator<Lawyer>() {
                @Override
                public int compare(Lawyer l1, Lawyer l2) {
                    return l1.getFirstname().compareTo(l2.getFirstname());
                }
            });
        }
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
        int httpStatus;
        if (lawyer == null){
            httpStatus=404;
        } else {
            httpStatus=200;
        }
        return Response
                .status(httpStatus)
                .entity(lawyer)
                .build();
    }
}
