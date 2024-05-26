package www.UserAccount.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.UserAccount.com.DAO.AddressRepository;
import www.UserAccount.com.DAO.CustomerRepository;
import www.UserAccount.com.Entity.Address;
import www.UserAccount.com.Entity.Customer;

import java.util.List;

@Service
public class CustomerServicse {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;

    public void customerDelete(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Fount Customer"));
        for (Address address : customer.getAddresses()) {
            address.getCustomers().remove(customer);
            if (address.getCustomers().isEmpty()) {
                addressRepository.delete(address);
            } else {
                addressRepository.save(address);
            }
        }
        customerRepository.delete(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }


}
