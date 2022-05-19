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

    public Lawyer getLawyer() {
        return lawyer;
    }

    public void setLawyer(Lawyer lawyer) {
        this.lawyer = lawyer;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public Double getNetworth() {
        return networth;
    }

    public void setNetworth(Double networth) {
        this.networth = networth;
    }
}
