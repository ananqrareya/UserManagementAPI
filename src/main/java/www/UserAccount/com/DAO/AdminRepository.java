package www.UserAccount.com.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import www.UserAccount.com.Entity.Admin;
import www.UserAccount.com.Entity.User;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByUser(User user);

    Admin findById(int id);
}
