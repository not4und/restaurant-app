package restaurant.dto.response;

import java.util.List;

public class ShoppingCartResponseDto {
    private Long id;
    private List<Long> shoppingCartItemIds;

    public ShoppingCartResponseDto(Long id, List<Long> shoppingCartItemIds) {
        this.id = id;
        this.shoppingCartItemIds = shoppingCartItemIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getShoppingCartItemIds() {
        return shoppingCartItemIds;
    }

    public void setShoppingCartItemIds(List<Long> shoppingCartItemIds) {
        this.shoppingCartItemIds = shoppingCartItemIds;
    }
}
