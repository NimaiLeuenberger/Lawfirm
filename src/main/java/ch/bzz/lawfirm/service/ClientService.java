package ch.bzz.lawfirm.service;

import ch.bzz.lawfirm.model.Client;
import ch.bzz.lawfirm.data.DataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * client service
 */

@Path("client")
public class ClientService {

    /**
     * lists all clients
     *
     * @return clientList
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listClients(){
        List<Client> clientList = DataHandler.getInstance().readAllClients();
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
        return Response
                .status(200)
                .entity(client)
                .build();
    }
}
