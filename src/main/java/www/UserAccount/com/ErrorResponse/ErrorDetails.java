package www.UserAccount.com.ErrorResponse;

import java.util.Date;

public class ErrorDetails {
    private String status;
    private String message;
    private Date date;

    public ErrorDetails() {

    }

    public ErrorDetails(String status, String message, Date date) {
        this.status = status;
        this.message = message;
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
