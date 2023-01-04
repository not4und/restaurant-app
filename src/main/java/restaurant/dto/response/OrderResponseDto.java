package restaurant.dto.response;

import java.time.LocalDateTime;

public class OrderResponseDto {
    private Long id;
    private Long productId;
    private int amount;
    private boolean done;
    private LocalDateTime orderTime;

    public OrderResponseDto(Long id, Long productId, int amount,
                            boolean done, LocalDateTime orderTime) {
        this.id = id;
        this.productId = productId;
        this.amount = amount;
        this.done = done;
        this.orderTime = orderTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }
}
