package ch.bzz.lawfirm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * a Client from a Lawyer
 */

public class Client {
    private int clientID;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String telNumber;
    private Double networth;
    @JsonIgnore
    private Lawyer lawyer;

    /**
     * gets clientID.
     *
     * @return value of clientID
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * sets clientID.
     *
     * @param clientID value of clientID
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     * gets firstname.
     *
     * @return value of firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * sets firstname.
     *
     * @param firstname value of firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * gets lastname.
     *
     * @return value of lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * sets lastname.
     *
     * @param lastname value of lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * gets birthdate.
     *
     * @return value of birthdate
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * sets birthdate.
     *
     * @param birthdate value of birthdate
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * gets telNumber.
     *
     * @return value of telNumber
     */
    public String getTelNumber() {
        return telNumber;
    }

    /**
     * sets telNumber.
     *
     * @param telNumber value of telNumber
     */
    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    /**
     * gets networth.
     *
     * @return value of networth
     */
    public Double getNetworth() {
        return networth;
    }

    /**
     * sets networth.
     *
     * @param networth value of networth
     */
    public void setNetworth(Double networth) {
        this.networth = networth;
    }

    /**
     * gets lawyer.
     *
     * @return value of lawyer
     */
    public Lawyer getLawyer() {
        return lawyer;
    }

    /**
     * sets lawyer.
     *
     * @param lawyer value of lawyer
     */
    public void setLawyer(Lawyer lawyer) {
        this.lawyer = lawyer;
    }
}
