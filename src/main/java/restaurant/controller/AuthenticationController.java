package restaurant.controller;

import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import restaurant.dto.request.UserLoginRequestDto;
import restaurant.dto.request.UserRegistrationRequestDto;
import restaurant.exception.AuthenticationException;
import restaurant.model.User;
import restaurant.security.AuthenticationService;
import restaurant.security.jwt.JwtTokenProvider;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(AuthenticationService authenticationService,
                                    JwtTokenProvider jwtTokenProvider) {
        this.authenticationService = authenticationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid
                                               UserRegistrationRequestDto requestDto) {
        return createToken(authenticationService.register(requestDto.getEmail(),
                requestDto.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginRequestDto requestDto)
            throws AuthenticationException {
        return createToken(authenticationService.login(requestDto.getEmail(),
                requestDto.getPassword()));
    }

    private ResponseEntity<Object> createToken(User user) {
        return new ResponseEntity<>(Map.of("token",
                jwtTokenProvider.createToken(user.getEmail(), user.getRoles().stream()
                        .map(r -> r.getRoleName().name())
                        .collect(Collectors.toList()))), HttpStatus.OK);
    }
}
