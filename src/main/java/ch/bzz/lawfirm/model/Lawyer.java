package ch.bzz.lawfirm.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * a Lawyer from a company
 */

public class Lawyer {
    /*@JsonIgnore
    private Client[] clients;
    @JsonIgnore
    private int cntr = 0;

    private LegalCase[] legalCase;
    */

    private int lawyerID;
    private String firstname;
    private String lastname;
    private String company;
    private int experience;
    private String type;
    private Double winrate;

    /**
     * gets lawyerID.
     *
     * @return value of lawyerID
     */
    public int getLawyerID() {
        return lawyerID;
    }

    /**
     * sets lawyerID.
     *
     * @param lawyerID value of lawyerID
     */
    public void setLawyerID(int lawyerID) {
        this.lawyerID = lawyerID;
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
     * gets company.
     *
     * @return value of company
     */
    public String getCompany() {
        return company;
    }

    /**
     * sets company.
     *
     * @param company value of company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * gets experience.
     *
     * @return value of experience
     */
    public int getExperience() {
        return experience;
    }

    /**
     * sets experience.
     *
     * @param experience value of experience
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * gets type.
     *
     * @return value of type
     */
    public String getType() {
        return type;
    }

    /**
     * sets type.
     *
     * @param type value of type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * gets winrate.
     *
     * @return value of winrate
     */
    public Double getWinrate() {
        return winrate;
    }

    /**
     * sets winrate.
     *
     * @param winrate value of winrate
     */
    public void setWinrate(Double winrate) {
        this.winrate = winrate;
    }

    /*   public Client getClient(int index) {
        return clients[index];
    }

    public void addClients(Client client) {
        this.clients[cntr] = client;
    }

    public LegalCase[] getLegalCase() {
        return legalCase;
    }

    public void setLegalCase(LegalCase[] legalCase) {
        this.legalCase = legalCase;
    }

    */

}
