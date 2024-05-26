package www.UserAccount.com.Event;

import org.springframework.context.ApplicationEvent;

public class UserAdminApproveEvent extends ApplicationEvent {
    private String email;
    private String userName;

    public UserAdminApproveEvent(Object source, String email, String userName) {
        super(source);
        this.email = email;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
