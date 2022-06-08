package ch.bzz.lawfirm.service;

import ch.bzz.lawfirm.data.DataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * test service
 */
@Path("test")
public class TestService {

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {

        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }


    /**
     * restores the json-files
     * @return Response
     */

    @GET
    @Path("restore")
    @Produces(MediaType.TEXT_PLAIN)
    public Response restore() {
        try {
            java.nio.file.Path path = Paths.get(Config.getProperty("lawyerJSON"));
            String filename = path.getFileName().toString();
            String folder = path.getParent().toString();

            byte[] lawyerJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            FileOutputStream fileOutputStream = new FileOutputStream(Config.getProperty("lawyerJSON"));
            fileOutputStream.write(lawyerJSON);

            path = Paths.get(Config.getProperty("clientJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] clientJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("clientJSON"));
            fileOutputStream.write(clientJSON);

            path = Paths.get(Config.getProperty("legalcaseJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] legalcaseJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("legalcaseJSON"));
            fileOutputStream.write(legalcaseJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataHandler.initLists();
        return Response
                .status(200)
                .entity("Erfolgreich")
                .build();
    }
}
