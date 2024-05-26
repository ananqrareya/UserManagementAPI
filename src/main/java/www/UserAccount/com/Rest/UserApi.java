package www.UserAccount.com.Rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import www.UserAccount.com.DTO.UserDto;
import www.UserAccount.com.Entity.User;
import www.UserAccount.com.Event.UserRegisterEventPublisher;
import www.UserAccount.com.service.UserServices;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRegisterEventPublisher eventPublisher;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @ModelAttribute UserDto userDto) {
        try {
            User user = userServices.newUser(userDto);
            eventPublisher.setPublisher(user);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error registering user: " + e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) throws Exception {

        try {
            User user = userServices.verifyToken(token);
            if (user.isActive() && user.getRole().getRoleName().equalsIgnoreCase("Customer")) {
                return ResponseEntity.ok("Account successfully verified and activated.");
            } else if (user.getRole().getRoleName().equalsIgnoreCase("Admin")) {
                return ResponseEntity.ok("Your information has been confirmed, however " + '\n' + " But the truth is that your account will play a role Admin .\n" +
                        "Wait for you to receive final approval from system administrators");
            } else {
                return ResponseEntity.badRequest().body("Account verification failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
