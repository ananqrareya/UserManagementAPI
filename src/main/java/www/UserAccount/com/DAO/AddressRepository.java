package www.UserAccount.com.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import www.UserAccount.com.Entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
