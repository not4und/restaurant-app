package restaurant.service.impl;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import restaurant.dao.UserDao;
import restaurant.model.Role;
import restaurant.model.User;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final Long ID = 1L;
    private static final String EMAIL = "user@e.mail";
    private static final String PASSWORD = "1q2w3e4r";
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDao userDao;
    @Spy
    private PasswordEncoder passwordEncoder;
    private User user;

    @BeforeEach
    void setUp() {
        user = createUser(ID);

    }

    @Test
    void add_ok() {
        User inputUser = createUser(null);
        Mockito.when(userDao.add(inputUser)).thenReturn(user);
        Assertions.assertEquals(user, userService.add(inputUser));
    }

    @Test
    void get_ok() {
        Mockito.when(userDao.get(ID)).thenReturn(Optional.of(user));
        Assertions.assertEquals(user, userService.get(ID));
    }

    @Test
    void get_notFound_notOk() {
        Mockito.when(userDao.get(ID)).thenReturn(Optional.empty());
        Assertions.assertEquals("User with id: " + ID + " not found.",
                Assertions.assertThrows(RuntimeException.class,
                        () -> userService.get(ID)).getMessage());
    }

    @Test
    void findByEmail_ok() {
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userDao.findByEmail(EMAIL)).thenReturn(userOptional);
        Assertions.assertEquals(userOptional, userService.findByEmail(EMAIL));
    }

    @Test
    void findByEmail_notFound_notOk() {
        Mockito.when(userDao.findByEmail(EMAIL)).thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(), userService.findByEmail(EMAIL));
    }

    private User createUser(Long id) {
        Role role = new Role(Role.RoleName.USER);
        role.setId(ID);
        User newUser = new User(EMAIL, passwordEncoder.encode(PASSWORD), Set.of(role));
        newUser.setId(id);
        return newUser;
    }
}
