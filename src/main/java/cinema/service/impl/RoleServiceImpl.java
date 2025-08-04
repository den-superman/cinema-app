package cinema.service.impl;

import cinema.dao.RoleDao;
import cinema.model.Role;
import cinema.security.RoleName;
import cinema.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
  private final RoleDao roleDao;

  public RoleServiceImpl(RoleDao roleDao) {
    this.roleDao = roleDao;
  }

  @Override
  public Role add(Role role) {
    return roleDao.save(role);
  }

  @Override
  public Role getByRole(RoleName roleName) {
    return roleDao.getByRoleName(roleName);
  }
}
