package restaurant.dao;

import java.util.List;
import java.util.Optional;
import restaurant.model.Role;

public interface RoleDao {
    Role add(Role role);

    List<Role> getAll();

    Optional<Role> getByName(Role.RoleName roleName);
}
