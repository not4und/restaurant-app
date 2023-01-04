package restaurant.security.jwt;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;
import restaurant.exception.InvalidJwtAuthenticationException;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {
    private static final String EMAIL = "user@e.mail";
    private static final String PASSWORD = "1q2w3e4r";
    private static final String ROLE = "USER";
    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private HttpServletRequest httpServletRequest;
    private String token;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "secret");
        ReflectionTestUtils.setField(jwtTokenProvider, "validityInMilliseconds",
                604800000L, long.class);
        token = jwtTokenProvider.createToken(EMAIL, List.of(ROLE));
    }

    @Test
    void createToken_ok() {
        Assertions.assertNotNull(token);
    }

    @Test
    void getUsername_ok() {
        Assertions.assertEquals(EMAIL, jwtTokenProvider.getUsername(token));
    }

    @Test
    void getAuthentication_ok() {
        UserDetails userDetails = User.withUsername(EMAIL).password(PASSWORD).roles(ROLE).build();
        Mockito.when(userDetailsService.loadUserByUsername(ArgumentMatchers.any()))
                .thenReturn(userDetails);
        User actual = (User) jwtTokenProvider.getAuthentication(token).getPrincipal();
        Assertions.assertEquals(userDetails.getUsername(), actual.getUsername());
        Assertions.assertEquals(userDetails.getPassword(), actual.getPassword());
        Assertions.assertEquals(userDetails.getAuthorities(), actual.getAuthorities());
    }

    @Test
    void resolveToken_ok() {
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer " + token);
        Assertions.assertEquals(token, jwtTokenProvider.resolveToken(httpServletRequest));
    }

    @Test
    void resolveToken_nullToken_notOk() {
        resolveTokenAssertNullTest(null);
    }

    @Test
    void resolveToken_notBearerToken_notOk() {
        resolveTokenAssertNullTest(token);
    }

    @Test
    void validateToken_ok() {
        Assertions.assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void validateToken_invalidToken_notOk() {
        Assertions.assertEquals("Invalid JWT token.",
                Assertions.assertThrows(InvalidJwtAuthenticationException.class,
                        () -> jwtTokenProvider.validateToken("invalid_token")).getMessage());
    }

    private void resolveTokenAssertNullTest(String token) {
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn(token);
        Assertions.assertNull(jwtTokenProvider.resolveToken(httpServletRequest));
    }
}
