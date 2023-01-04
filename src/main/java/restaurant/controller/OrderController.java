package restaurant.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.dto.request.OrderRequestDto;
import restaurant.dto.response.OrderResponseDto;
import restaurant.model.Order;
import restaurant.model.User;
import restaurant.service.OrderService;
import restaurant.service.ProductService;
import restaurant.service.TableService;
import restaurant.service.UserService;
import restaurant.service.mapper.RequestDtoMapper;
import restaurant.service.mapper.ResponseDtoMapper;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final TableService tableService;
    private final UserService userService;
    private final ProductService productService;
    private final RequestDtoMapper<OrderRequestDto, Order> orderRequestDtoMapper;
    private final ResponseDtoMapper<OrderResponseDto, Order> orderResponseDtoMapper;

    public OrderController(OrderService orderService,
                           TableService tableService,
                           UserService userService,
                           ProductService productService,
                           RequestDtoMapper<OrderRequestDto, Order> orderRequestDtoMapper,
                           ResponseDtoMapper<OrderResponseDto, Order> orderResponseDtoMapper) {
        this.orderService = orderService;
        this.tableService = tableService;
        this.userService = userService;
        this.productService = productService;
        this.orderRequestDtoMapper = orderRequestDtoMapper;
        this.orderResponseDtoMapper = orderResponseDtoMapper;
    }

    @GetMapping
    public List<OrderResponseDto> getAll() {
        return parseListOfResponse(orderService.getAll());
    }

    @GetMapping("/by-table/{id}")
    public List<OrderResponseDto> getAllByTable(@PathVariable Long id) {
        return parseListOfResponse(orderService.getAllByTable(tableService.get(id)));
    }

    @GetMapping("/by-user")
    public List<OrderResponseDto> getAllByUser(Authentication auth) {
        return parseListOfResponse(orderService
                .getAllByUser(userService.get(parseUser(auth).getId())));
    }

    @PostMapping("/add")
    public OrderResponseDto add(Authentication auth,
                                @RequestBody OrderRequestDto requestDto) {
        Order order = orderRequestDtoMapper.mapToModel(requestDto);
        order.setUser(parseUser(auth));
        return orderResponseDtoMapper.mapToDto(orderService.add(order));
    }

    @PutMapping("/done/{id}")
    public OrderResponseDto done(@PathVariable Long id) {
        Order order = orderService.get(id);
        order.setDone(true);
        return orderResponseDtoMapper.mapToDto(orderService.update(order));
    }

    private List<OrderResponseDto> parseListOfResponse(List<Order> orders) {
        return orders.stream()
                .map(orderResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private User parseUser(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        return userService.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email: " + email + " not found.")
        );
    }
}
