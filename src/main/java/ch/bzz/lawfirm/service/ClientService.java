package ch.bzz.lawfirm.service;

import ch.bzz.lawfirm.model.Client;
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
 * client service
 */

@Path("client")
public class ClientService {

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
            @QueryParam("sort") boolean sort
    ){
        List<Client> clientList = DataHandler.getInstance().readAllClients();
        if (sort){
            Collections.sort(clientList, new Comparator<Client>() {
                @Override
                public int compare(Client c1, Client c2) {
                    return c1.getFirstname().compareTo(c2.getFirstname());
                }
            });
        }
        return Response
                .status(200)
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
            @QueryParam("id") int clientID
    ){
        Client client = DataHandler.getInstance().readClientByID(clientID);
        int httpStatus;
        if (client == null){
            httpStatus=404;
        } else {
            httpStatus=200;
        }
        return Response
                .status(httpStatus)
                .entity(client)
                .build();
    }
}
