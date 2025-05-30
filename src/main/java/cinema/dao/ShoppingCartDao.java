package cinema.dao;

import cinema.model.ShoppingCart;
import cinema.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartDao extends JpaRepository<ShoppingCart, Long> {

    ShoppingCart getByUser(User user);
}
