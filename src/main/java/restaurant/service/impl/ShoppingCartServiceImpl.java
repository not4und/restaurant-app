package restaurant.service.impl;

import org.springframework.stereotype.Service;
import restaurant.dao.ShoppingCartDao;
import restaurant.dao.ShoppingCartItemDao;
import restaurant.model.ShoppingCart;
import restaurant.model.ShoppingCartItem;
import restaurant.model.User;
import restaurant.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartDao shoppingCartDao;
    private final ShoppingCartItemDao shoppingCartItemDao;

    public ShoppingCartServiceImpl(ShoppingCartDao shoppingCartDao,
                                   ShoppingCartItemDao shoppingCartItemDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.shoppingCartItemDao = shoppingCartItemDao;
    }

    @Override
    public ShoppingCart registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartDao.add(shoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user);
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        return shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart addShoppingCartItem(ShoppingCartItem shoppingCartItem, User user) {
        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user);
        shoppingCart.getShoppingCartItems().add(shoppingCartItemDao.add(shoppingCartItem));
        return shoppingCartDao.update(shoppingCart);
    }
}
