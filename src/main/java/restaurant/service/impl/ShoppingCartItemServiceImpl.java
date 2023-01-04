package restaurant.service.impl;

import org.springframework.stereotype.Service;
import restaurant.dao.ShoppingCartItemDao;
import restaurant.model.ShoppingCartItem;
import restaurant.service.ShoppingCartItemService;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {
    private final ShoppingCartItemDao shoppingCartItemDao;

    public ShoppingCartItemServiceImpl(ShoppingCartItemDao shoppingCartItemDao) {
        this.shoppingCartItemDao = shoppingCartItemDao;
    }

    @Override
    public ShoppingCartItem add(ShoppingCartItem shoppingCartItem) {
        return shoppingCartItemDao.add(shoppingCartItem);
    }

    @Override
    public ShoppingCartItem update(ShoppingCartItem shoppingCartItem) {
        return shoppingCartItemDao.update(shoppingCartItem);
    }

    @Override
    public ShoppingCartItem get(Long id) {
        return shoppingCartItemDao.get(id).orElseThrow(
                () -> new RuntimeException("Shopping cart item with id: " + id + " not found.")
        );
    }

    @Override
    public boolean delete(Long id) {
        return shoppingCartItemDao.delete(id);
    }
}
