package restaurant.security.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import restaurant.exception.AuthenticationException;
import restaurant.model.Role;
import restaurant.model.ShoppingCart;
import restaurant.model.User;
import restaurant.service.RoleService;
import restaurant.service.ShoppingCartService;
import restaurant.service.UserService;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    private static final String EMAIL = "user@e.mail";
    private static final String PASSWORD = "1q2w3e4r";
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private ShoppingCartService shoppingCartService;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;
    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role(Role.RoleName.USER);
        role.setId(1L);
        user = new User(EMAIL, PASSWORD, Set.of(role));
        user.setId(1L);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Test
    void register_ok() {
        Mockito.when(roleService.getByName(Role.RoleName.USER)).thenReturn(role);
        Mockito.when(userService.add(ArgumentMatchers.any())).thenReturn(user);
        ShoppingCart shoppingCart = new ShoppingCart(user, new ArrayList<>());
        Mockito.when(shoppingCartService.registerNewShoppingCart(user)).thenReturn(shoppingCart);
        Assertions.assertEquals(user, authenticationService.register(EMAIL, PASSWORD));
    }

    @Test
    void login_ok() throws AuthenticationException {
        Mockito.when(userService.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        Assertions.assertEquals(user, authenticationService.login(EMAIL, PASSWORD));
    }

    @Test
    void login_incorrectData_notOk() {
        Mockito.when(userService.findByEmail(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Assertions.assertEquals("Incorrect login data.",
                Assertions.assertThrows(AuthenticationException.class,
                        () -> authenticationService.login(EMAIL, PASSWORD)).getMessage());
    }
}
