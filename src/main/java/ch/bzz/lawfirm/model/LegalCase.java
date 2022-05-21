package ch.bzz.lawfirm.model;

public class LegalCase {
    private int legalCaseID;
    private String accuser;
    private String defendant;
    private String description;

    /**
     * gets legalCaseID.
     *
     * @return value of legalCaseID
     */
    public int getLegalCaseID() {
        return legalCaseID;
    }

    /**
     * sets legalCaseID.
     *
     * @param legalCaseID value of legalCaseID
     */
    public void setLegalCaseID(int legalCaseID) {
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

    /**
     * gets description.
     *
     * @return value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets description.
     *
     * @param description value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
