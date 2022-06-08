package ch.bzz.lawfirm.service;

import ch.bzz.lawfirm.model.Lawyer;
import ch.bzz.lawfirm.data.DataHandler;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * lawyer service
 */

@Path("lawyer")
public class LawyerService{
    private int lawyerCntr = 3;

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
        List<Lawyer> lawyerList = DataHandler.readAllLawyers();
        if (sort) {
            Collections.sort(lawyerList, new Comparator<Lawyer>() {
                @Override
                public int compare(Lawyer l1, Lawyer l2) {
                    return l1.getName().compareTo(l2.getName());
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
            @NotEmpty
            @Pattern(regexp = "[1-9][0-9]?[0-9]?")
            @QueryParam("id") String lawyerID
    ){
        int httpStatus = 200;
        Lawyer lawyer = DataHandler.readLawyerByID(lawyerID);
        if (lawyer == null){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(lawyer)
                .build();
    }

    /**
     * inserts a new lawyer
     * @param lawyer a valid lawyer
     * @param clientID the id of the client
     * @return Response
     */

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertLawyer(
            @Valid @BeanParam Lawyer lawyer,
            @FormParam("clientID") String clientID
    ){
        lawyer.setClientID(clientID);
        lawyer.setLawyerID(newLawyerIDGenerator());

        DataHandler.insertLawyer(lawyer);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    public String newLawyerIDGenerator(){
        String newLawyerID = String.valueOf(lawyerCntr);
        for (Lawyer l : DataHandler.readAllLawyers()){
            if (l.getLawyerID().equals(newLawyerID)){
                lawyerCntr++;
                newLawyerID = String.valueOf(lawyerCntr);
            }
        }
        return newLawyerID;
    }

    /**
     * updates a new lawyer
     * @param lawyer a valid lawyer
     * @param clientID the id of the client
     * @return Response
     */

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateLawyer(
            @Valid @BeanParam Lawyer lawyer,
            @FormParam("clientID") String clientID
    ){
        int httpStatus = 200;
        Lawyer oldlawyer = DataHandler.readLawyerByID(lawyer.getLawyerID());
        if (oldlawyer != null){
            oldlawyer.setName(lawyer.getName());
            oldlawyer.setExperience(lawyer.getExperience());
            oldlawyer.setWinrate(lawyer.getWinrate());
            oldlawyer.setClientID(clientID);

            DataHandler.updateLawyer();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a lawyer identified by its id
     * @param lawyerID the id of the lawyer
     * @return Response
     */

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteLawyer(
            @NotEmpty
            @Pattern(regexp = "[1-9][0-9]?[0-9]?")
            @QueryParam("id") String lawyerID
    ){
        int httpStatus = 200;
        if (!DataHandler.deleteLawyer(lawyerID)){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
