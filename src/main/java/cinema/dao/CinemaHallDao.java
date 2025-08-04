package cinema.dao;

import cinema.model.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaHallDao extends JpaRepository<CinemaHall, Long> {}
