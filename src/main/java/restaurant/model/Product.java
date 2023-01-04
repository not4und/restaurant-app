package restaurant.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private BigDecimal price;
    private int weight;
    private boolean deleted;
    @ManyToOne
    private Category category;

    public Product() {
    }

    public Product(String name, BigDecimal price, int weight, boolean deleted, Category category) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.deleted = deleted;
        this.category = category;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category categories) {
        this.category = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return weight == product.weight
                && deleted == product.deleted
                && Objects.equals(id, product.id)
                && Objects.equals(name, product.name)
                && price.compareTo(product.price) == 0
                && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, weight, deleted, category);
    }

    @Override
    public String toString() {
        return "Product{"
                + " id=" + id
                + ", name='" + name + '\''
                + ", price=" + price
                + ", weight=" + weight
                + ", deleted=" + deleted
                + ", category=" + category
                + '}';
    }
}
