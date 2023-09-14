package ss8.security.service;

import ss8.security.model.dto.request.FormRegisterDto;
import ss8.security.model.entity.Users;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<Users> findAll();
    Optional<Users> findById(Long id);
    Optional<Users> findByUsername(String username);
    Users save(FormRegisterDto registerDto);

}
