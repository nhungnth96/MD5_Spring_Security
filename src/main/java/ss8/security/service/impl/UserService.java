package ss8.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ss8.security.model.dto.request.FormRegisterDto;
import ss8.security.model.entity.Role;
import ss8.security.model.entity.RoleName;
import ss8.security.model.entity.Users;
import ss8.security.repository.IUserRepository;
import ss8.security.service.IRoleService;
import ss8.security.service.IUserService;

import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        return  userRepository.findById(id);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Users save(FormRegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new RuntimeException("Username is existed");
        }
        // lấy danh sách các quyền và chuyển thành đối tượng user

        Set<Role> roles = new HashSet<>();
        System.out.println(registerDto.getRoles());
        if(registerDto.getRoles()==null||registerDto.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        } else {
        registerDto.getRoles().forEach(
                role->{switch (role){
                    case "admin":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                    case "seller":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_SELLER));
                    case "user":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
                }}
        );

            }

        return userRepository.save( Users.builder()
                .name(registerDto.getName())
                .username(registerDto.getUsername())
                .password(encoder.encode(registerDto.getPassword()))
                .status(true)
                .roles(roles)
                .build());
    }


}
