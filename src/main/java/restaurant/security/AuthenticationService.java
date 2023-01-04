package restaurant.security;

import restaurant.exception.AuthenticationException;
import restaurant.model.User;

public interface AuthenticationService {
    User register(String email, String password);

    User login(String email, String password) throws AuthenticationException;
}
