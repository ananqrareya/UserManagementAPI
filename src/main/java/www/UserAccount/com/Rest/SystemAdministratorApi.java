package www.UserAccount.com.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import www.UserAccount.com.DAO.AdminRepository;
import www.UserAccount.com.Entity.Admin;
import www.UserAccount.com.Entity.Customer;
import www.UserAccount.com.Entity.User;
import www.UserAccount.com.Event.UserRegisterEventPublisher;
import www.UserAccount.com.service.CustomerServicse;
import www.UserAccount.com.service.UserServices;

import java.util.List;

@RestController
@RequestMapping("/api/administrator")
public class SystemAdministratorApi {
    @Autowired
    private UserServices userServices;
    @Autowired
    private CustomerServicse customerServicse;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRegisterEventPublisher eventPublisher;


    @PostMapping("/approve/{id}")
    public ResponseEntity<String> approveAdmin(@PathVariable int id) {
        User user = userServices.approveUserAdmin(id);
        if (user.isActive()) {
            eventPublisher.setUserApprovePublisher(user);
            return ResponseEntity.ok("The account has been fully activated");
        } else
            return ResponseEntity.ok("Error activating the account");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByUser(@PathVariable int id) {

        userServices.deleteById(id);
        return ResponseEntity.ok("Delete BY user");

    }

    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> allUserCustomer() {
        List<Customer> customers = customerServicse.findAll();
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/admin")
    private ResponseEntity<List<Admin>> allUserAdmin() {
        List<Admin> admins = adminRepository.findAll();
        return ResponseEntity.ok().body(admins);
    }
}
