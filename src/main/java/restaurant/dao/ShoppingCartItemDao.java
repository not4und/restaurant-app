package restaurant.dao;

import java.util.Optional;
import restaurant.model.ShoppingCartItem;

public interface ShoppingCartItemDao {
    ShoppingCartItem add(ShoppingCartItem shoppingCartItem);

    ShoppingCartItem update(ShoppingCartItem shoppingCartItem);

    Optional<ShoppingCartItem> get(Long id);

    boolean delete(Long id);
}
