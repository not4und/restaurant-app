package restaurant.config;

import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import restaurant.model.Role;
import restaurant.model.User;
import restaurant.service.RoleService;
import restaurant.service.UserService;

@Component
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;

    public DataInitializer(RoleService roleService,
                           UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void inject() {
        if (roleService.getAll().isEmpty()) {
            Role admin = new Role();
            admin.setRoleName(Role.RoleName.ADMIN);
            Role user = new Role();
            user.setRoleName(Role.RoleName.USER);
            roleService.add(admin);
            roleService.add(user);
        }
        if (userService.findByEmail("admin@gmail.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword("1q2w3e4r");
            admin.setRoles(Set.of(roleService.getByName(Role.RoleName.ADMIN)));
            userService.add(admin);
        }
    }
}
