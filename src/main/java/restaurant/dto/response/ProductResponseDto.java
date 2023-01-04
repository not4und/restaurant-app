package restaurant.dto.response;

import java.math.BigDecimal;

public class ProductResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private int weight;
    private Long categoryId;

    public ProductResponseDto(Long id, String name, BigDecimal price,
                              int weight, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
