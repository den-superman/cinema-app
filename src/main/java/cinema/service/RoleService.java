package cinema.service;

import cinema.model.Role;
import cinema.security.RoleName;

public interface RoleService {
  Role add(Role role);

  Role getByRole(RoleName roleName);
}
