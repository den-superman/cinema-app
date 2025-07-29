package cinema.dao;

import cinema.model.Role;
import cinema.security.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

  Role getByRoleName(RoleName roleName);
}
