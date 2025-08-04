package cinema.model;

import cinema.security.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Entity
public class Role {
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "roleName")
  private RoleName roleName;

    public void setRole(RoleName roleName) {
    this.roleName = roleName;
  }

    @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Role role = (Role) o;
    return id.equals(role.id) && roleName == role.roleName;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, roleName);
  }
}
