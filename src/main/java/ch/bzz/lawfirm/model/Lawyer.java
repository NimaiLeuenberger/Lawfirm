package ch.bzz.lawfirm.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * a Lawyer from a company
 */

public class Lawyer {
    /*@JsonIgnore
    private Client[] clients;*/
    @JsonIgnore
    private int cntr = 0;

    private int lawyerID;
    private String firstname;
    private String lastname;
    private String company;
    private int experience;
    private String type;
    private Double winrate;


    public int getLawyerID() {
        return lawyerID;
    }

    public void setLawyerID(int lawyerID) {
        this.lawyerID = lawyerID;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getWinrate() {
        return winrate;
    }

    public void setWinrate(Double winrate) {
        this.winrate = winrate;
    }

 /*   public Client getClient(int index) {
        return clients[index];
    }

    public void addClients(Client client) {
        this.clients[cntr] = client;
    }*/
}
