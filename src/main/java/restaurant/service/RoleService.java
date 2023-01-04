package restaurant.service;

import java.util.List;
import restaurant.model.Role;

public interface RoleService {
    Role add(Role role);

    List<Role> getAll();

    Role getByName(Role.RoleName roleName);
}
