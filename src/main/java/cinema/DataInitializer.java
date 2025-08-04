package cinema;

import cinema.model.*;
import cinema.security.RoleName;
import cinema.service.*;
import java.time.LocalDateTime;
import java.util.Set;

// @Component
public class DataInitializer {
  private final RoleService roleService;
  private final UserService userService;
  private final MovieService movieService;
  private final CinemaHallService cinemaHallService;
  private final MovieSessionService movieSessionService;

  public DataInitializer(
      RoleService roleService,
      UserService userService,
      MovieService movieService,
      CinemaHallService cinemaHallService,
      MovieSessionService movieSessionService) {
    this.roleService = roleService;
    this.userService = userService;
    this.movieService = movieService;
    this.cinemaHallService = cinemaHallService;
    this.movieSessionService = movieSessionService;
  }

  //    @PostConstruct
  public void inject() {
    createAdmin();
    insertMovies();
  }

  private void insertMovies() {
    Movie harryPotter = new Movie();
    harryPotter.setTitle("Harry Potter");
    harryPotter.setDescription("Fantasy movie");
    movieService.add(harryPotter);

    Movie cars = new Movie();
    cars.setTitle("Cars");
    cars.setDescription("Cartoon for children");
    movieService.add(cars);

    CinemaHall red = new CinemaHall(10, 10);
    red.setDescription("Red");
    cinemaHallService.add(red);

    MovieSession harryPotterSession = new MovieSession(red.getRows(), red.getSeatsInRow());
    harryPotterSession.setMovie(harryPotter);
    harryPotterSession.setCinemaHall(red);
    harryPotterSession.setShowTime(LocalDateTime.now().plusYears(2));
    movieSessionService.add(harryPotterSession);
  }

  private void createAdmin() {
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
