package restaurant.dao.impl;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurant.dao.RoleDao;
import restaurant.model.Role;

class RoleDaoImplTest extends AbstractTest {
    private RoleDao roleDao;
    private Role role;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {Role.class};
    }

    @BeforeEach
    void setUp() {
        roleDao = new RoleDaoImpl(getSessionFactory());
        role = new Role(Role.RoleName.USER);
    }

    @Test
    void add_ok() {
        Role actual = roleDao.add(role);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(role, actual);
    }

    @Test
    void getAll_ok() {
        roleDao.add(role);
        Role role1 = new Role(Role.RoleName.ADMIN);
        roleDao.add(role1);
        List<Role> actual = roleDao.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertTrue(actual.containsAll(List.of(role, role1)));
    }

    @Test
    void getByName_ok() {
        roleDao.add(role);
        Optional<Role> actual = roleDao.getByName(Role.RoleName.USER);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(role, actual.get());
    }
}
