package ch.bzz.lawfirm.service;

import ch.bzz.lawfirm.model.Lawyer;
import ch.bzz.lawfirm.model.LegalCase;
import ch.bzz.lawfirm.data.DataHandler;
import jdk.jfr.TransitionFrom;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * legal case service
 */

@Path("legalCase")
public class LegalCaseService {
    private int legalCaseCntr = 3;

    /**
     * lists all legal cases
     *
     * @return legalCaseList
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listLegalCases(
            @QueryParam("sort") boolean sort
    ){
        List<LegalCase> legalCaseList = DataHandler.readAllLegalCases();
        if (sort) {
            Collections.sort(legalCaseList, new Comparator<LegalCase>() {
                @Override
                public int compare(LegalCase lc1, LegalCase lc2) {
                    return lc1.getAccuser().compareTo(lc2.getAccuser());
                }
            });
        }
        return Response
                .status(200)
                .entity(legalCaseList)
                .build();
    }

    /**
     * reads a legal case by its id
     *
     * @param legalCaseID
     * @return a legal case
     */

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readLegalCase(
            @NotEmpty
            @Pattern(regexp = "[1-9][0-9]?[0-9]?")
            @QueryParam("id") String legalCaseID
    ){
        LegalCase legalCase = DataHandler.readLegalCaseByID(legalCaseID);
        int httpStatus;
        if (legalCase == null){
            httpStatus=404;
        } else {
            httpStatus=200;
        }
        return Response
                .status(httpStatus)
                .entity(legalCase)
                .build();
    }

    /**
     * inserts a legal case
     * @param legalCase a valid legal case
     * @return Response
     */

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertLegalCase(
            @Valid @BeanParam LegalCase legalCase
    ){
        legalCase.setLegalCaseID(newLegalCaseIDGenerator());

        DataHandler.insertLegalCase(legalCase);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    public String newLegalCaseIDGenerator(){
        String newLegalCaseID = String.valueOf(legalCaseCntr);
        for (Lawyer l : DataHandler.readAllLawyers()){
            if (l.getLawyerID().equals(newLegalCaseID)){
                legalCaseCntr++;
                newLegalCaseID = String.valueOf(legalCaseCntr);
            }
        }
        return newLegalCaseID;
    }

    /**
     * updates a new legal case
     * @param legalCase a valid legal case
     * @return Response
     */

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateLegalCase(
            @Valid @BeanParam LegalCase legalCase
    ){
        int httpStatus = 200;
        LegalCase oldLegalCase = DataHandler.readLegalCaseByID(legalCase.getLegalCaseID());
        if (oldLegalCase != null){
            oldLegalCase.setAccuser(legalCase.getAccuser());
            oldLegalCase.setDefendant(legalCase.getDefendant());

            DataHandler.updateLegalCase();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a legal case identified by its id
     * @param legalCaseID the id of the legal case
     * @return Response
     */

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteLegalCase(
            @NotEmpty
            @Pattern(regexp = "[1-9][0-9]?[0-9]?")
            @QueryParam("id") String legalCaseID
    ){
        int httpStatus = 200;
        if (!DataHandler.deleteLegalCase(legalCaseID)){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
