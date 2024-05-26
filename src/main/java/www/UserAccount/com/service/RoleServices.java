package www.UserAccount.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.UserAccount.com.DAO.RoleRepository;
import www.UserAccount.com.Entity.Role;

@Service
public class RoleServices {
    @Autowired
    private RoleRepository roleRepository;

    public Role findByRoleName(String roleName) {
        Role role = roleRepository.findByRoleName(roleName).orElseGet(() -> {
            if (!roleRepository.existsByRoleName(roleName)) {
                Role newRole = new Role();
                newRole.setRoleName(roleName);
                return roleRepository.save(newRole);
            }
            return null;
        });
        if (role == null) {
            throw new RuntimeException("Role in already exists :" + roleName);
        }
        return role;
    }
}
