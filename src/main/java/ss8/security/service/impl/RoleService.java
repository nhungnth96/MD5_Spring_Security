package ss8.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ss8.security.model.entity.Role;
import ss8.security.model.entity.RoleName;
import ss8.security.repository.IRoleRepository;
import ss8.security.service.IRoleService;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Role findByRoleName(RoleName roleName) {
        Optional<Role> roleOptional = roleRepository.findByRoleName(roleName);
        if(roleOptional.isPresent()){
            return roleOptional.get();
        } else {
            throw new RuntimeException("Role not found");
        }


    }
}
