package restaurant.security.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import restaurant.model.Role;
import restaurant.model.User;
import restaurant.service.UserService;

import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    private static final String EMAIL = "user@e.mail";
    private static final String PASSWOD = "1q2w3e4r";

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        Role role = new Role(Role.RoleName.USER);
        role.setId(1L);
        user = new User(EMAIL, PASSWOD, Set.of(role));
        user.setId(1L);
    }

    @Test
    void loadUserByUsername_ok() {
        Mockito.when(userService.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        UserDetails actual = customUserDetailsService.loadUserByUsername(EMAIL);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(user.getEmail(), actual.getUsername());
        Assertions.assertEquals(user.getPassword(), actual.getPassword());
    }

    @Test
    void loadUserByUsername_notFound_notOk() {
        Mockito.when(userService.findByEmail(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Assertions.assertEquals("Can't find user by email: " + EMAIL,
                Assertions.assertThrows(UsernameNotFoundException.class,
                        () -> customUserDetailsService.loadUserByUsername(EMAIL)).getMessage());
    }
}
