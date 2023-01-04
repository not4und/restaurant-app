package restaurant.service.mapper.impl;

import org.springframework.stereotype.Component;
import restaurant.dto.request.ProductRequestDto;
import restaurant.dto.response.ProductResponseDto;
import restaurant.model.Product;
import restaurant.service.CategoryService;
import restaurant.service.mapper.RequestDtoMapper;
import restaurant.service.mapper.ResponseDtoMapper;

@Component
public class ProductDtoMapper implements RequestDtoMapper<ProductRequestDto, Product>,
        ResponseDtoMapper<ProductResponseDto, Product> {
    private final CategoryService categoryService;

    public ProductDtoMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Product mapToModel(ProductRequestDto dto) {
        return new Product(
                dto.getName(),
                dto.getPrice(),
                dto.getWeight(),
                false,
                categoryService.get(dto.getCategoryId())
        );
    }

    @Override
    public ProductResponseDto mapToDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getWeight(),
                product.getCategory().getId()
        );
    }
}
