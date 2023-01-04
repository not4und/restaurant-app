package restaurant.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import restaurant.dao.RoleDao;
import restaurant.model.Role;
import restaurant.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role add(Role role) {
        return roleDao.add(role);
    }

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public Role getByName(Role.RoleName roleName) {
        return roleDao.getByName(roleName).orElseThrow(
                () -> new RuntimeException("Role with name: " + roleName + " not found.")
        );
    }
}
