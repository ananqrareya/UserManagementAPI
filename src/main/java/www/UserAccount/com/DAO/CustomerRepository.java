package www.UserAccount.com.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import www.UserAccount.com.Entity.Customer;
import www.UserAccount.com.Entity.User;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByUser(User user);
}
