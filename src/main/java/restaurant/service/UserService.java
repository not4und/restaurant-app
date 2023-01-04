package restaurant.service;

import java.util.Optional;
import restaurant.model.User;

public interface UserService {
    User add(User user);

    User get(Long id);

    Optional<User> findByEmail(String email);
}
