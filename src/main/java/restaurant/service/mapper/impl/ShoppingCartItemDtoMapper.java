package restaurant.service.mapper.impl;

import org.springframework.stereotype.Component;
import restaurant.dto.response.ShoppingCartItemResponseDto;
import restaurant.model.ShoppingCartItem;
import restaurant.service.mapper.ResponseDtoMapper;

@Component
public class ShoppingCartItemDtoMapper
        implements ResponseDtoMapper<ShoppingCartItemResponseDto, ShoppingCartItem> {
    @Override
    public ShoppingCartItemResponseDto mapToDto(ShoppingCartItem shoppingCartItem) {
        return new ShoppingCartItemResponseDto(
                shoppingCartItem.getId(),
                shoppingCartItem.getProduct().getId(),
                shoppingCartItem.getAmount()
        );
    }
}
