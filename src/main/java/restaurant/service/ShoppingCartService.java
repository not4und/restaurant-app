package restaurant.service;

import restaurant.model.ShoppingCart;
import restaurant.model.ShoppingCartItem;
import restaurant.model.User;

public interface ShoppingCartService {
    ShoppingCart registerNewShoppingCart(User user);

    ShoppingCart getByUser(User user);

    ShoppingCart update(ShoppingCart shoppingCart);

    ShoppingCart addShoppingCartItem(ShoppingCartItem shoppingCartItem, User user);
}
