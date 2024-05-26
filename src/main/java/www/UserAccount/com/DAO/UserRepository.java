package www.UserAccount.com.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.UserAccount.com.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByVerificationToken(String token);

}
