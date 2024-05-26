package www.UserAccount.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import www.UserAccount.com.DAO.AddressRepository;
import www.UserAccount.com.DAO.AdminRepository;
import www.UserAccount.com.DAO.CustomerRepository;
import www.UserAccount.com.DAO.UserRepository;
import www.UserAccount.com.DTO.AddressDto;
import www.UserAccount.com.DTO.UserDto;
import www.UserAccount.com.Entity.*;
import www.UserAccount.com.ErrorResponse.TokenExpiredException;
import www.UserAccount.com.ErrorResponse.TokenNotFoundException;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleServices roleServices;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private CustomerServicse customerServicse;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User newUser(UserDto userDto) {
        String roleName = userDto.getRoleName();
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber((userDto.getPhoneNumber()));
        user.setUserName(userDto.getUsername());
        user.setPasswords(passwordEncoder.encode(userDto.getPassword()));
        String token = generateActivationToken(user);
        user.setVerificationToken(token);
        user.setTokenExpirationDate(new Timestamp(System.currentTimeMillis() + 86400000));
        Role role = roleServices.findByRoleName(userDto.getRoleName());
        user.setRole(role);
        if (role.getRoleName().equalsIgnoreCase("Customer")) {
            if (userDto.getBalance() == null) {
                throw new RuntimeException("Cannot proceed without a valid balance.");
            } else if (userDto.getAddressDto() == null || userDto.getAddressDto().isEmpty()) {
                throw new RuntimeException("Customer must have at least one address.");
            } else {
                Customer customer = new Customer();
                customer.setUser(user);
                customer.setBalance(userDto.getBalance());
                for (AddressDto addressDto : userDto.getAddressDto()) {
                    Address address = new Address();
                    address.setCity(addressDto.getCity());
                    address.setRegion(addressDto.getRegion());
                    address.setStreet(addressDto.getStreet());
                    address.setBuildingNo(addressDto.getBuildingNumber());
                    addressRepository.save(address);
                    customer.getAddresses().add(address);
                }
                customerRepository.save(customer);
            }
        } else if (role.getRoleName().equalsIgnoreCase("Admin")) {
            if (userDto.getPowerDescription() == null) {
                throw new RuntimeException("Cannot proceed without a valid power descriptions");
            } else {
                Admin admin = new Admin();
                admin.setUser(user);
                admin.setPowerDescription(userDto.getPowerDescription());
                adminRepository.save(admin);
            }

        }
        return userRepository.save(user);
    }

    public String generateActivationToken(User user) {
        String token = UUID.randomUUID().toString();
        return token;

    }

    public User verifyToken(String token) throws Exception {
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            throw new TokenNotFoundException("Token not found.");
        }
        if (user.getTokenExpirationDate().before(new Timestamp(System.currentTimeMillis()))) {
            throw new TokenExpiredException("Token has expired.");
        }
        if (user.getRole().getRoleName().equalsIgnoreCase("Admin")) {
            user.setVerificationToken(null);
            user.setTokenExpirationDate(null);
        } else {
            user.setActive(true);
            user.setVerificationToken(null);
            user.setTokenExpirationDate(null);
        }
        return userRepository.save(user);
    }

    public User approveUserAdmin(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found User the :" + id));
        ;
        if (user.getVerificationToken() != null)
            throw new RuntimeException("This account has not confirmed its information to move to the final stage");
        user.setActive(true);
        return userRepository.save(user);
    }

    public void deleteById(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not Found User the :" + userId));
        if ("Customer".equalsIgnoreCase(user.getRole().getRoleName())) {
            Customer customer = customerRepository.findByUser(user);
            if (customer != null) {
                customerServicse.customerDelete(customer.getId());
            }
        } else if ("Admin".equalsIgnoreCase(user.getRole().getRoleName())) {
            Admin admin = adminRepository.findByUser(user);
            if (admin != null) {
                adminRepository.delete(admin);
            }

        }
        userRepository.delete(user);
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

}
