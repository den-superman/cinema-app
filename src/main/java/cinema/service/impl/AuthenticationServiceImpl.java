package cinema.service.impl;

import cinema.model.User;
import cinema.security.RoleName;
import cinema.service.AuthenticationService;
import cinema.service.RoleService;
import cinema.service.UserService;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserService userService;
  private final RoleService roleService;

  public AuthenticationServiceImpl(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @Override
  public User register(String email, String password) {
    User user = new User();
    user.setEmail(email);
    user.setPassword(password);
    user.setRoles(Set.of(roleService.getByRole(RoleName.USER)));
    userService.add(user);
    return user;
  }
}
