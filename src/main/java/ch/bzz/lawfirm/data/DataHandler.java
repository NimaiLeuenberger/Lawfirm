package ch.bzz.lawfirm.data;

import ch.bzz.lawfirm.model.Client;
import ch.bzz.lawfirm.model.Lawyer;
import ch.bzz.lawfirm.model.LegalCase;
import ch.bzz.lawfirm.service.Config;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public final class DataHandler {
    private static List<Lawyer> lawyerList;
    private static List<Client> clientList;
    private static List<LegalCase> legalCaseList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
    }

    /**
     * initializes the lists
     */

    public static void initLists() {
        DataHandler.setLawyerList(null);
        DataHandler.setClientList(null);
        DataHandler.setLegalCaseList(null);
    }

    /**
     * reads all lawyers
     * @return list of lawyers
     */
    public static List<Lawyer> readAllLawyers() {
        return getLawyerList();
    }

    /**
     * reads a lawyer by its id
     * @param lawyerID
     * @return the Lawyer (null=not found)
     */
    public static Lawyer readLawyerByID(String lawyerID) {
        Lawyer lawyer = null;
        for (Lawyer entry : getLawyerList()) {
            if (entry.getLawyerID().equals(lawyerID)) {
                lawyer = entry;
            }
        }
        return lawyer;
    }

    /**
     * inserts a new lawyer into the lawyerList
     *
     * @param lawyer the lawyer to be saved
     */
    public static void insertLawyer(Lawyer lawyer) {
        getLawyerList().add(lawyer);
        writeLawyerJSON();
    }

    /**
     * updates the lawyerList
     */
    public static void updateLawyer() {
        writeLawyerJSON();
    }

    /**
     * deletes a book identified by the bookUUID
     * @param lawyerID  the key
     * @return  success=true/false
     */
    public static boolean deleteLawyer(String lawyerID) {
        Lawyer lawyer = readLawyerByID(lawyerID);
        if (lawyer != null) {
            getLawyerList().remove(lawyer);
            writeLawyerJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all Clients
     * @return list of clients
     */
    public static List<Client> readAllClients() {
        return getClientList();
    }

    /**
     * reads a clients by its id
     * @param clientID
     * @return the Client (null=not found)
     */
    public static Client readClientByID(String clientID) {
        Client client = null;
        for (Client entry : getClientList()) {
            if (entry.getClientID().equals(clientID)) {
                client = entry;
            }
        }
        return client;
    }

    /**
     * inserts a new client into the clientList
     *
     * @param client the client to be saved
     */
    public static void insertClient(Client client) {
        getClientList().add(client);
        writeClientJSON();
    }

    /**
     * updates the clientList
     */
    public static void updateClient() {
        writeClientJSON();
    }

    /**
     * deletes a client identified by the clientID
     * @param clientID  the key
     * @return  success=true/false
     */
    public static boolean deleteClient(String clientID) {
        Client client = readClientByID(clientID);
        if (client != null) {
            getClientList().remove(client);
            writeClientJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all Legal Cases
     * @return list of legal cases
     */
    public static List<LegalCase> readAllLegalCases() {
        return getLegalCaseList();
    }

    /**
     * reads a legal case by its id
     * @param legalCaseID
     * @return the legal case (null=not found)
     */
    public static LegalCase readLegalCaseByID(String legalCaseID) {
        LegalCase legalCase = null;
        for (LegalCase entry : getLegalCaseList()) {
            if (entry.getLegalCaseID().equals(legalCaseID)) {
                legalCase = entry;
            }
        }
        return legalCase;
    }

    /**
     * inserts a new legal case into the legalCaseList
     *
     * @param legalCase the legal case to be saved
     */
    public static void insertLegalCase(LegalCase legalCase) {
        getLegalCaseList().add(legalCase);
        writeLegalCaseJSON();
    }

    /**
     * updates the legalCaseList
     */
    public static void updateLegalCase() {
        writeLegalCaseJSON();
    }

    /**
     * deletes a legal case identified by the legalCaseID
     * @param legalCaseID the key
     * @return  success=true/false
     */
    public static boolean deleteLegalCase(String legalCaseID) {
        LegalCase legalCase = readLegalCaseByID(legalCaseID);
        if (legalCase != null) {
            getLegalCaseList().remove(legalCase);
            writeLegalCaseJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads the lawyers from the JSON-file
     */
    private static void readLawyerJSON() {
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
     * writes the lawyerList to the JSON-file
     */
    private static void writeLawyerJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String lawyerPath = Config.getProperty("lawyerJSON");
        try {
            fileOutputStream = new FileOutputStream(lawyerPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getLawyerList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the clients from the JSON-file
     */
    private static void readClientJSON() {
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
     * writes the clientList to the JSON-file
     */
    private static void writeClientJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String clientPath = Config.getProperty("clientJSON");
        try {
            fileOutputStream = new FileOutputStream(clientPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getClientList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the legal cases from the JSON-file
     */
    private static void readLegalCaseJSON() {
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
     * writes the legalCaseList to the JSON-file
     */
    private static void writeLegalCaseJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String legalCasePath = Config.getProperty("legalcaseJSON");
        try {
            fileOutputStream = new FileOutputStream(legalCasePath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getLegalCaseList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * gets lawyerList
     *
     * @return value of lawyerList
     */
    private static List<Lawyer> getLawyerList() {
        if (lawyerList == null){
            setLawyerList(new ArrayList<>());
            readLawyerJSON();
        }
        return lawyerList;
    }

    /**
     * sets lawyerList
     *
     * @param lawyerList the value to set
     */
    private static void setLawyerList(List<Lawyer> lawyerList) {
        DataHandler.lawyerList = lawyerList;
    }

    /**
     * gets clientList
     *
     * @return value of clientList
     */
    private static List<Client> getClientList() {
        if (clientList == null){
            setClientList(new ArrayList<>());
            readClientJSON();
        }
        return clientList;
    }

    /**
     * sets clientList
     *
     * @param clientList the value to set
     */
    private static void setClientList(List<Client> clientList) {
        DataHandler.clientList = clientList;
    }

    /**
     * gets legalCaseList
     *
     * @return value of legalCaseList
     */
    private static List<LegalCase> getLegalCaseList() {
        if (legalCaseList == null){
            setLegalCaseList(new ArrayList<>());
            readLegalCaseJSON();
        }
        return legalCaseList;
    }

    /**
     * sets legalCaseList
     *
     * @param legalCaseList the value to set
     */
    private static void setLegalCaseList(List<LegalCase> legalCaseList) {
        DataHandler.legalCaseList = legalCaseList;
    }
}