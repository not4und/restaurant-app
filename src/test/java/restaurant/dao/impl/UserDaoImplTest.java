package restaurant.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurant.dao.RoleDao;
import restaurant.dao.UserDao;
import restaurant.exception.DataProcessingException;
import restaurant.model.Role;
import restaurant.model.User;

import java.util.Optional;
import java.util.Set;

class UserDaoImplTest extends AbstractTest {
    private UserDao userDao;
    private User user;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {User.class, Role.class};
    }

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl(getSessionFactory());
        RoleDao roleDao = new RoleDaoImpl(getSessionFactory());
        user = new User("user@e.mail", "1q2w3e4r",
                Set.of(roleDao.add(new Role(Role.RoleName.USER))));
    }

    @Test
    void add_ok() {
        User actual = userDao.add(user);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(user, actual);
    }

    @Test
    void add_notUniqueEmail() {
        userDao.add(user);
        Assertions.assertEquals("Can't insert "
                + user.getClass().getSimpleName() + ": " + user,
                Assertions.assertThrows(DataProcessingException.class,
                        () -> userDao.add(user)).getMessage());
    }

    @Test
    void get_ok() {
        userDao.add(user);
        Optional<User> actual = userDao.get(1L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(user, actual.get());
    }

    @Test
    void findByEmail_ok() {
        userDao.add(user);
        Optional<User> actual = userDao.findByEmail("user@e.mail");
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(user, actual.get());
    }

    @Test
    void findByEmail_notFound_notOk() {
        Assertions.assertTrue(userDao.findByEmail("user@e.mail").isEmpty());
    }
}
