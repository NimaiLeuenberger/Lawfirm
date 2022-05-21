package ch.bzz.lawfirm.data;

import ch.bzz.lawfirm.model.Client;
import ch.bzz.lawfirm.model.Lawyer;
import ch.bzz.lawfirm.model.LegalCase;
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
    private List<LegalCase> legalCaseList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setLawyerList(new ArrayList<>());
        readLawyerJSON();
        setClientList(new ArrayList<>());
        readClientJSON();
        setLegalCaseList(new ArrayList<>());
        readLegalCaseJSON();
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
     * @param clientID
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
     * reads all Legal Cases
     * @return list of legal cases
     */
    public List<LegalCase> readAllLegalCases() {
        return getLegalCaseList();
    }

    /**
     * reads a legal case by its id
     * @param legalCaseID
     * @return the legal case (null=not found)
     */
    public LegalCase readLegalCaseByID(int legalCaseID) {
        LegalCase legalCase = null;
        for (LegalCase entry : getLegalCaseList()) {
            if (entry.getLegalCaseID()== (legalCaseID)) {
                legalCase = entry;
            }
        }
        return legalCase;
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
     * reads the legal cases from the JSON-file
     */
    private void readLegalCaseJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("legalcaseJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            LegalCase[] legalCases = objectMapper.readValue(jsonData, LegalCase[].class);
            for (LegalCase legalCase : legalCases) {
                getLegalCaseList().add(legalCase);
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

    /**
     * gets legalCaseList
     *
     * @return value of legalCaseList
     */
    private List<LegalCase> getLegalCaseList() {
        return legalCaseList;
    }

    /**
     * sets legalCaseList
     *
     * @param legalCaseList the value to set
     */
    private void setLegalCaseList(List<LegalCase> legalCaseList) {
        this.legalCaseList = legalCaseList;
    }
}