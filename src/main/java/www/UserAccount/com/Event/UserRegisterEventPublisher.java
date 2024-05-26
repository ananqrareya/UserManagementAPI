package www.UserAccount.com.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import www.UserAccount.com.Entity.User;

@Component
public class UserRegisterEventPublisher {
    @Autowired
    private ApplicationEventPublisher publisher;

    public void setPublisher(User user) {

        UserRegisterEvent event = new UserRegisterEvent(this, user.getEmail(), user.getUserName(), user.getVerificationToken());
        publisher.publishEvent(event);
    }

    public void setUserApprovePublisher(User user) {
        UserAdminApproveEvent event = new UserAdminApproveEvent(this, user.getEmail(), user.getUserName());
        publisher.publishEvent(event);
    }

}
