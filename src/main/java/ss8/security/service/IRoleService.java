package ss8.security.service;

import ss8.security.model.entity.Role;
import ss8.security.model.entity.RoleName;

import java.util.Optional;

public interface IRoleService {
    Role findByRoleName(RoleName roleName);
}
