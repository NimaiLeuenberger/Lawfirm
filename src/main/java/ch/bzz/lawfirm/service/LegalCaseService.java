package ch.bzz.lawfirm.service;

import ch.bzz.lawfirm.model.Lawyer;
import ch.bzz.lawfirm.model.LegalCase;
import ch.bzz.lawfirm.data.DataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * legal case service
 */

@Path("legalCase")
public class LegalCaseService {

    /**
     * lists all legal cases
     *
     * @return legalCaseList
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listLegalCases(){
        List<LegalCase> legalCaseList = DataHandler.getInstance().readAllLegalCases();
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
            @QueryParam("id") int legalCaseID
    ){
        LegalCase legalCase = DataHandler.getInstance().readLegalCaseByID(legalCaseID);
        return Response
                .status(200)
                .entity(legalCase)
                .build();
    }
}
