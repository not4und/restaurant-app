package restaurant.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restaurant.dto.response.ShoppingCartResponseDto;
import restaurant.model.ShoppingCart;
import restaurant.model.ShoppingCartItem;
import restaurant.model.User;
import restaurant.service.ProductService;
import restaurant.service.ShoppingCartItemService;
import restaurant.service.ShoppingCartService;
import restaurant.service.UserService;
import restaurant.service.mapper.ResponseDtoMapper;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final ProductService productService;
    private final ShoppingCartItemService shoppingCartItemService;
    private final ResponseDtoMapper<ShoppingCartResponseDto, ShoppingCart>
            shoppingCartResponseDtoMapper;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  UserService userService,
                                  ProductService productService,
                                  ShoppingCartItemService shoppingCartItemService,
                                  ResponseDtoMapper<ShoppingCartResponseDto, ShoppingCart>
                                          shoppingCartResponseDtoMapper) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.productService = productService;
        this.shoppingCartItemService = shoppingCartItemService;
        this.shoppingCartResponseDtoMapper = shoppingCartResponseDtoMapper;
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getByUser(Authentication auth) {
        return shoppingCartResponseDtoMapper.mapToDto(shoppingCartService
                .getByUser(parseUser(auth)));
    }

    @PutMapping("/shopping-cart-items/add/{id}")
    public void addToCart(Authentication auth, @PathVariable Long id) {
        shoppingCartService.addShoppingCartItem(shoppingCartItemService
                .add(new ShoppingCartItem(productService.get(id), 1)),
                parseUser(auth));
    }

    @PutMapping("/clear")
    public ShoppingCartResponseDto clear(Authentication auth) {
        ShoppingCart shoppingCart = shoppingCartService.getByUser(parseUser(auth));
        shoppingCart.getShoppingCartItems().clear();
        return shoppingCartResponseDtoMapper.mapToDto(shoppingCartService.update(shoppingCart));
    }

    @DeleteMapping("/delete-item/{id}")
    public ShoppingCartResponseDto deleteItem(Authentication auth,
                                              @RequestParam Long id) {
        ShoppingCart shoppingCart = shoppingCartService.getByUser(parseUser(auth));
        shoppingCart.getShoppingCartItems()
                .remove(shoppingCartItemService.get(id));
        return shoppingCartResponseDtoMapper.mapToDto(shoppingCartService.update(shoppingCart));
    }

    private User parseUser(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        return userService.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email: " + email + " not found.")
        );
    }
}
