package ch.bzz.lawfirm.data;

import ch.bzz.lawfirm.model.Client;
import ch.bzz.lawfirm.model.Lawyer;
import ch.bzz.lawfirm.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Lawyer> lawyerList;
    private List<Client> clientList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setLawyerList(new ArrayList<>());
        readLawyerJSON();
        setClientList(new ArrayList<>());
        readClientJSON();
    }

    /**
     * gets the only instance of this class
     * @return instance
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }


    /**
     * reads all lawyers
     * @return list of lawyers
     */
    public List<Lawyer> readAllLawyers() {
        return getLawyerList();
    }

    /**
     * reads a lawyer by its id
     * @param lawyerID
     * @return the Lawyer (null=not found)
     */
    public Lawyer readLawyerByID(int lawyerID) {
        Lawyer lawyer = null;
        for (Lawyer entry : getLawyerList()) {
            if (entry.getLawyerID() == (lawyerID)) {
                lawyer = entry;
            }
        }
        return lawyer;
    }

    /**
     * reads all Clients
     * @return list of clients
     */
    public List<Client> readAllClients() {

        return getClientList();
    }

    /**
     * reads a clients by its id
     * @param
     * @return the Client (null=not found)
     */
    public Client readClientByID(int clientID) {
        Client client = null;
        for (Client entry : getClientList()) {
            if (entry.getClientID()== (clientID)) {
                client = entry;
            }
        }
        return client;
    }

    /**
     * reads the lawyers from the JSON-file
     */
    private void readLawyerJSON() {
        try {
            String path = Config.getProperty("lawyerJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Lawyer[] lawyers = objectMapper.readValue(jsonData, Lawyer[].class);
            for (Lawyer lawyer : lawyers) {
                getLawyerList().add(lawyer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the clients from the JSON-file
     */
    private void readClientJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("clientJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Client[] clients = objectMapper.readValue(jsonData, Client[].class);
            for (Client client : clients) {
                getClientList().add(client);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * gets lawyerList
     *
     * @return value of lawyerList
     */
    private List<Lawyer> getLawyerList() {
        return lawyerList;
    }

    /**
     * sets lawyerList
     *
     * @param lawyerList the value to set
     */
    private void setLawyerList(List<Lawyer> lawyerList) {
        this.lawyerList = lawyerList;
    }

    /**
     * gets clientList
     *
     * @return value of clientList
     */
    private List<Client> getClientList() {
        return clientList;
    }

    /**
     * sets clientList
     *
     * @param clientList the value to set
     */
    private void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }


}