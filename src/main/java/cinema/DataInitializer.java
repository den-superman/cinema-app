package cinema;

import cinema.model.Role;
import cinema.model.User;
import cinema.security.RoleName;
import cinema.service.RoleService;
import cinema.service.UserService;
import java.util.Set;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

//@Component
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;

    public DataInitializer(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

//    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRole(RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRole(RoleName.USER);
        roleService.add(userRole);
        User user = new User();
        user.setEmail("admin");
        user.setPassword("admin");
        user.setRoles(Set.of(adminRole));
        userService.add(user);
    }
}
