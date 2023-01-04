package restaurant.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restaurant.dto.response.ShoppingCartItemResponseDto;
import restaurant.model.ShoppingCartItem;
import restaurant.service.ShoppingCartItemService;
import restaurant.service.mapper.ResponseDtoMapper;

@RestController
@RequestMapping("/shopping-cart-items")
public class ShoppingCartItemController {
    private final ShoppingCartItemService shoppingCartItemService;
    private final ResponseDtoMapper<ShoppingCartItemResponseDto, ShoppingCartItem>
            shoppingCartItemResponseDtoMapper;

    public ShoppingCartItemController(ShoppingCartItemService shoppingCartItemService,
                                      ResponseDtoMapper<ShoppingCartItemResponseDto,
                                              ShoppingCartItem>
                                              shoppingCartItemResponseDtoMapper) {
        this.shoppingCartItemService = shoppingCartItemService;
        this.shoppingCartItemResponseDtoMapper = shoppingCartItemResponseDtoMapper;
    }

    @PutMapping("/increase/{id}")
    public ShoppingCartItemResponseDto increase(@PathVariable Long id) {
        return shoppingCartItemResponseDtoMapper.mapToDto(shoppingCartItemService
                .update(changeAmountByOne(id, 1)));
    }

    @PutMapping("/degrease/{id}")
    public ShoppingCartItemResponseDto degrease(@PathVariable Long id) {
        return shoppingCartItemResponseDtoMapper.mapToDto(shoppingCartItemService
                .update(changeAmountByOne(id, -1)));
    }

    @PutMapping("/set-amount/{id}")
    public ShoppingCartItemResponseDto setAmount(@PathVariable Long id,
                                                 @RequestParam int amount) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemService.get(id);
        shoppingCartItem.setAmount(amount);
        return shoppingCartItemResponseDtoMapper.mapToDto(shoppingCartItemService
                .update(shoppingCartItem));
    }

    private ShoppingCartItem changeAmountByOne(Long id, int value) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemService.get(id);
        shoppingCartItem.setAmount(shoppingCartItem.getAmount() + value);
        return shoppingCartItem;
    }
}
