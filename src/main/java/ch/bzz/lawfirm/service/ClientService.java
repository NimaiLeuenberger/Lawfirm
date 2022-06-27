package ch.bzz.lawfirm.service;

import ch.bzz.lawfirm.model.Client;
import ch.bzz.lawfirm.data.DataHandler;
import ch.bzz.lawfirm.model.Lawyer;
import ch.bzz.lawfirm.util.Birthdate;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * client service
 */

@Path("client")
public class ClientService {
    private int clientCntr = 3;

    /**
     * lists all clients
     *
     * @param sort
     * @return clientList
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listClients(
            @CookieParam("userRole") String userRole,
            @QueryParam("sort") boolean sort
    ){
        int httpStatus;
        List<Client> clientList = null;

        if (userRole == null || userRole.equals("guest")){
            httpStatus = 401;
        } else {
            httpStatus = 200;
            clientList = DataHandler.readAllClients();
            if (sort){
                Collections.sort(clientList, new Comparator<Client>() {
                    @Override
                    public int compare(Client c1, Client c2) {
                        return c1.getName().compareTo(c2.getName());
                    }
                });
            }
        }

        return Response
                .status(httpStatus)
                .entity(clientList)
                .build();
    }

    /**
     * reads a client by its id
     *
     * @param clientID
     * @return a client
     */

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readClient(
            @CookieParam("userRole") String userRole,
            @NotEmpty
            @Pattern(regexp = "[1-9][0-9]?[0-9]?")
            @QueryParam("id") String clientID
    ){
        int httpStatus;
        Client client = null;

        if (userRole == null || userRole.equals("guest")){
            httpStatus = 401;
        } else {
            client = DataHandler.readClientByID(clientID);
            if (client == null){
                httpStatus=410;
            } else {
                httpStatus = 200;
            }
        }
        return Response
                .status(httpStatus)
                .entity(client)
                .build();
    }

    /**
     * inserts a new client
     * @param client a valid client
     * @param legalCaseID the id of the legal case
     * @return Response
     */

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertClient(
            @CookieParam("userRole") String userRole,
            @Valid @BeanParam Client client,
            @FormParam("legalCaseID") String legalCaseID,
            @FormParam("birthdate") @Birthdate(value = 1900)
                String birthdate
    ){
        int httpStatus;

        if (userRole == null || userRole.equals("guest") || userRole.equals("user")){
            httpStatus = 401;
        } else {
            httpStatus = 200;
            client.setLegalCaseID(legalCaseID);
            client.setClientID(newClientIDGenerator());
            client.setBirthdate(birthdate);

            DataHandler.insertClient(client);
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    public String newClientIDGenerator(){
        String newClientID = String.valueOf(clientCntr);
        for (Lawyer l : DataHandler.readAllLawyers()){
            if (l.getLawyerID().equals(newClientID)){
                clientCntr++;
                newClientID = String.valueOf(clientCntr);
            }
        }
        return newClientID;
    }

    /**
     * updates a new client
     * @param client a valid client
     * @param legalCaseID the id of the legal case
     * @return Response
     */

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateClient(
            @CookieParam("userRole") String userRole,
            @Valid @BeanParam Client client,
            @FormParam("legalCaseID") String legalCaseID,
            @FormParam("birthdate") @Birthdate(value = 1900)
                String birthdate
    ){
        int httpStatus;

        if (userRole == null || userRole.equals("guest") || userRole.equals("user")) {
            httpStatus = 401;
        } else {
            httpStatus = 200;

            Client oldClient = DataHandler.readClientByID(client.getClientID());
            if (oldClient != null){
                oldClient.setName(client.getName());
                oldClient.setBirthdate(birthdate);
                oldClient.setTelNumber(client.getTelNumber());
                oldClient.setLegalCaseID(legalCaseID);

                DataHandler.updateClient();
            } else {
                httpStatus = 410;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a client identified by its id
     * @param clientID the id of the client
     * @return Response
     */

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteLawyer(
            @CookieParam("userRole") String userRole,
            @NotEmpty
            @Pattern(regexp = "[1-9][0-9]?[0-9]?")
            @QueryParam("id") String clientID
    ){
        int httpStatus;

        if (userRole == null || userRole.equals("guest") || userRole.equals("user")) {
            httpStatus = 401;
        } else {
            httpStatus = 200;

            if (!DataHandler.deleteClient(clientID)){
                httpStatus = 410;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
