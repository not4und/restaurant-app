package restaurant.service;

import restaurant.model.ShoppingCartItem;

public interface ShoppingCartItemService {
    ShoppingCartItem add(ShoppingCartItem shoppingCartItem);

    ShoppingCartItem update(ShoppingCartItem shoppingCartItem);

    ShoppingCartItem get(Long id);

    boolean delete(Long id);
}
