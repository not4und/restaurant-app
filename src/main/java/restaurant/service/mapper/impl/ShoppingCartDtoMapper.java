package restaurant.service.mapper.impl;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import restaurant.dto.response.ShoppingCartResponseDto;
import restaurant.model.ShoppingCart;
import restaurant.model.ShoppingCartItem;
import restaurant.service.mapper.ResponseDtoMapper;

@Component
public class ShoppingCartDtoMapper
        implements ResponseDtoMapper<ShoppingCartResponseDto, ShoppingCart> {
    @Override
    public ShoppingCartResponseDto mapToDto(ShoppingCart shoppingCart) {
        return new ShoppingCartResponseDto(
                shoppingCart.getId(),
                shoppingCart.getShoppingCartItems().stream()
                        .map(ShoppingCartItem::getId)
                        .collect(Collectors.toList())
        );
    }
}
