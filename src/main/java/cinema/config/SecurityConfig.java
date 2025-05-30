package cinema.config;

import cinema.security.RoleName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/by-email").hasRole(RoleName.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/orders", "/shopping-carts/by-user").hasRole(RoleName.USER.name())
                .requestMatchers(HttpMethod.GET, "/cinema-halls", "/movies", "/movie-sessions/available")
                .hasAnyRole(RoleName.ADMIN.name(), RoleName.USER.name())
                .requestMatchers(HttpMethod.POST, "/cinema-halls", "/movies", "/movie-sessions").hasRole(RoleName.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/orders/complete").hasRole(RoleName.USER.name())
                .requestMatchers(HttpMethod.PUT, "/movie-sessions/{id}").hasRole(RoleName.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions").hasRole(RoleName.USER.name())
                .requestMatchers(HttpMethod.DELETE, "/movie-sessions/{id}").hasRole(RoleName.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin(formLogin ->
                        formLogin.loginPage("/login")
                                .defaultSuccessUrl("/movies")
//                                .failureForwardUrl("/cinema-halls")
                .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout") // Default is "/logout"
                        .logoutSuccessUrl("/login?logout=true") // Redirect after logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID") // Invalidate session cookie
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

}
