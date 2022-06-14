package ch.bzz.lawfirm.model;

import ch.bzz.lawfirm.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;

/**
 * a Lawyer from a company
 */

public class Lawyer {
    @FormParam("lawyerID")
    @Pattern(regexp = "[1-9][0-9]?[0-9]?")
    private String lawyerID;

    @FormParam("name")
    @Size(min = 6, max = 60)
    private String name;

    @FormParam("experience")
    @DecimalMin(value = "0.5")
    @DecimalMax(value = "64")
    private BigDecimal experience;

    @FormParam("winrate")
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "100")
    private BigDecimal winrate;

    @JsonIgnore
    private Client client;

    /**
     * gets the clientID from the Client-object
     * @return the clientID
     */
    public String getClientID() {
        if (getClient()== null) return null;
        return getClient().getClientID();
    }

    /**
     * creates a Client-object without the lawyerlist
     * @param clientID the key
     */
    public void setClientID(String clientID) {
        setClient(new Client());
        Client client = DataHandler.readClientByID(clientID);
        getClient().setClientID(clientID);
        getClient().setName(client.getName());
        getClient().setBirthdate(client.getBirthdate());
        getClient().setTelNumber(client.getTelNumber());
    }

    /**
     * gets lawyerID.
     *
     * @return value of lawyerID
     */
    public String getLawyerID() {
        return lawyerID;
    }

    /**
     * sets lawyerID.
     *
     * @param lawyerID value of lawyerID
     */
    public void setLawyerID(String lawyerID) {
        this.lawyerID = lawyerID;
    }

    /**
     * gets firstname.
     *
     * @return value of firstname
     */
    public String getName() {
        return name;
    }

    /**
     * sets firstname.
     *
     * @param name value of firstname
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets experience.
     *
     * @return value of experience
     */
    public BigDecimal getExperience() {
        return experience;
    }

    /**
     * sets experience.
     *
     * @param experience value of experience
     */
    public void setExperience(BigDecimal experience) {
        this.experience = experience;
    }

    /**
     * gets winrate.
     *
     * @return value of winrate
     */
    public BigDecimal getWinrate() {
        return winrate;
    }

    /**
     * sets winrate.
     *
     * @param winrate value of winrate
     */
    public void setWinrate(BigDecimal winrate) {
        this.winrate = winrate;
    }

    /**
     * gets client.
     *
     * @return value of client
     */
    public Client getClient() {
        return client;
    }

    /**
     * sets client.
     *
     * @param client value of client
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
