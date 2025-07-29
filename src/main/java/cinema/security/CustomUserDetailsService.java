package cinema.security;

import static org.springframework.security.core.userdetails.User.withUsername;

import cinema.model.User;
import cinema.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserService userService;

  public CustomUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user =
        userService
            .findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("No user found with email " + email));
    return withUsername(user.getEmail())
        .password(user.getPassword())
        .roles(user.getRoles().stream().map(i -> i.getRoleName().toString()).toArray(String[]::new))
        .build();
  }
}
