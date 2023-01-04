package restaurant.dto.response;

public class ShoppingCartItemResponseDto {
    private Long id;
    private Long productId;
    private int amount;

    public ShoppingCartItemResponseDto(Long id, Long productId, int amount) {
        this.id = id;
        this.productId = productId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
