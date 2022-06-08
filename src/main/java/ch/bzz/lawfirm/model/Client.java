package ch.bzz.lawfirm.model;

import ch.bzz.lawfirm.data.DataHandler;
import ch.bzz.lawfirm.util.LocalDateDeserializer;
import ch.bzz.lawfirm.util.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * a Client from a Lawyer
 */

public class Client {
    @FormParam("clientID")
    @Pattern(regexp = "[1-9][0-9]?[0-9]?")
    private String clientID;

    @FormParam("name")
    @Size(min = 6, max = 60)
    private String name;


    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @FormParam("birthdate")
    private LocalDate birthdate;

    @FormParam("telNumber")
    @Pattern(regexp = "0(2[1-246-7]|3[1-4]|4[13-4]|5[25-6]|6[1-2]|7[15-68-9]|8[17]|91)[0-9]{7}")
    private String telNumber;
    // regex for 10 digits, no space

    @JsonIgnore
    private LegalCase legalCase;

    /**
     * gets the legalCaseID from the Legal Case-object
     * @return the legalCaseID
     */
    public String getLegalCaseID() {
        if (getLegalCase()== null) return null;
        return getLegalCase().getLegalCaseID();
    }

    /**
     * creates a Legal Case-object without the lawyerlist
     * @param legalCaseID the key
     */
    public void setLegalCaseID(String legalCaseID) {
        setLegalCase(new LegalCase());
        LegalCase legalCase = DataHandler.readLegalCaseByID(legalCaseID);
        getLegalCase().setLegalCaseID(legalCaseID);
        getLegalCase().setAccuser(legalCase.getAccuser());
        getLegalCase().setDefendant(legalCase.getDefendant());
    }

    /**
     * gets clientID.
     *
     * @return value of clientID
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * sets clientID.
     *
     * @param clientID value of clientID
     */
    public void setClientID(String clientID) {
        this.clientID = clientID;
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
     * gets birthdate.
     *
     * @return value of birthdate
     */
    public LocalDate getBirthdate() {
        return birthdate;
    }

    /**
     * sets birthdate.
     *
     * @param birthdate value of birthdate
     */
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @JsonIgnore
    public void setBirthdate(String birthdate) {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.birthdate = LocalDate.parse(birthdate);
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
     * gets legalCase.
     *
     * @return value of legalCase
     */
    public LegalCase getLegalCase() {
        return legalCase;
    }

    /**
     * sets legalCase.
     *
     * @param legalCase value of legalCase
     */
    public void setLegalCase(LegalCase legalCase) {
        this.legalCase = legalCase;
    }
}
