package ch.bzz.lawfirm.exceptionHandling;

import java.io.Serializable;

public class LawfirmException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    public LawfirmException() {
        super();
    }
    public LawfirmException(String msg) {
        super(msg);
    }
    public LawfirmException(String msg, Exception e)  {
        super(msg, e);
    }
}
