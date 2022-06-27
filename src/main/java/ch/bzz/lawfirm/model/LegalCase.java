package ch.bzz.lawfirm.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

public class LegalCase {
    @FormParam("legalCaseID")
    @Pattern(regexp = "|[1-9][0-9]?[0-9]?")
    private String legalCaseID;

    @FormParam("accuser")
    @Size(min = 6, max = 80)
    private String accuser;

    @FormParam("defendant")
    @Size(min = 6, max = 80)
    private String defendant;

    /**
     * gets legalCaseID.
     *
     * @return value of legalCaseID
     */
    public String getLegalCaseID() {
        return legalCaseID;
    }

    /**
     * sets legalCaseID.
     *
     * @param legalCaseID value of legalCaseID
     */
    public void setLegalCaseID(String legalCaseID) {
        this.legalCaseID = legalCaseID;
    }

    /**
     * gets accuser.
     *
     * @return value of accuser
     */
    public String getAccuser() {
        return accuser;
    }

    /**
     * sets accuser.
     *
     * @param accuser value of accuser
     */
    public void setAccuser(String accuser) {
        this.accuser = accuser;
    }

    /**
     * gets defendant.
     *
     * @return value of defendant
     */
    public String getDefendant() {
        return defendant;
    }

    /**
     * sets defendant.
     *
     * @param defendant value of defendant
     */
    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }
}
