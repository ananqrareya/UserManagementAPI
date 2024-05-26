package www.UserAccount.com.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import www.UserAccount.com.Email.EmailSender;

@Component
public class EmailNotificationListener {
    @Autowired
    private EmailSender emailSender;
    @Value("${hostName}")
    private String hostName;

    @EventListener

    public void handleUserAddedEvent(UserRegisterEvent event) {
        String subject = "Welcome to Our Service Please Verify Your Account";
        String verificationUrl = "http://" + hostName + "/api/user/verify?token=" + event.getToken();
        String message = "Hello " + event.getUsername() + "Please click the link below to verify your account:\n" + verificationUrl;
        String to = event.getEmail();

        emailSender.setMailSender(to, subject, message);

    }

    @EventListener
    public void handleUserAdminApprove(UserAdminApproveEvent userAdminApprove) {
        String subject = "Agree and activate the account";
        String message = "Hello der/dear, I would like to inform you that your account   has been officially approved " + userAdminApprove.getUserName() + "and activated by system administrators.";
        String to = userAdminApprove.getEmail();
        emailSender.setMailSender(to, subject, message);
    }


}
