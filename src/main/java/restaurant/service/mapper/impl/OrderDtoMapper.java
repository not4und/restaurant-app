package restaurant.service.mapper.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;
import restaurant.dto.request.OrderRequestDto;
import restaurant.dto.response.OrderResponseDto;
import restaurant.model.Order;
import restaurant.service.ProductService;
import restaurant.service.TableService;
import restaurant.service.mapper.RequestDtoMapper;
import restaurant.service.mapper.ResponseDtoMapper;

@Component
public class OrderDtoMapper implements RequestDtoMapper<OrderRequestDto, Order>,
        ResponseDtoMapper<OrderResponseDto, Order> {
    private final ProductService productService;
    private final TableService tableService;

    public OrderDtoMapper(ProductService productService, TableService tableService) {
        this.productService = productService;
        this.tableService = tableService;
    }

    @Override
    public Order mapToModel(OrderRequestDto dto) {
        return new Order(
                productService.get(dto.getProductId()),
                dto.getAmount(),
                false,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                tableService.get(dto.getTableId()),
                null
        );
    }

    @Override
    public OrderResponseDto mapToDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getProduct().getId(),
                order.getAmount(),
                order.isDone(),
                order.getOrderTime()
        );
    }
}
