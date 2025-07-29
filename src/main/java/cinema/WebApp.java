package cinema;

import cinema.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(
    scanBasePackages = {"cinema", "cinema.service.impl"},
    scanBasePackageClasses = {UserServiceImpl.class})
public class WebApp {
  public static void main(String[] args) {
    SpringApplication.run(WebApp.class);
  }

  @Bean
  public PasswordEncoder getEncoder() {
    return new BCryptPasswordEncoder();
  }
}
