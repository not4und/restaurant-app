package restaurant.model;

import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    private Long id;
    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private User user;
    @OneToMany(orphanRemoval = true)
    @JoinTable(name = "shopping_carts_shopping_cart_items",
            joinColumns = @JoinColumn(name = "shopping_cart_id"),
            inverseJoinColumns = @JoinColumn(name = "shopping_cart_item_id"))
    private List<ShoppingCartItem> shoppingCartItems;

    public ShoppingCart() {
    }

    public ShoppingCart(User user, List<ShoppingCartItem> shoppingCartItems) {
        this.user = user;
        this.shoppingCartItems = shoppingCartItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id)
                && Objects.equals(user, that.user)
                && Objects.equals(shoppingCartItems, that.shoppingCartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, shoppingCartItems);
    }

    @Override
    public String toString() {
        return "ShoppingCart{"
                + " id=" + id
                + ", user=" + user
                + ", shoppingCartItems=" + shoppingCartItems
                + '}';
    }
}
