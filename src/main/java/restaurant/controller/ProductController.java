package restaurant.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.dto.request.ProductRequestDto;
import restaurant.dto.response.ProductResponseDto;
import restaurant.model.Product;
import restaurant.service.CategoryService;
import restaurant.service.ProductService;
import restaurant.service.mapper.RequestDtoMapper;
import restaurant.service.mapper.ResponseDtoMapper;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> productResponseDtoMapper;

    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             RequestDtoMapper<ProductRequestDto, Product>
                                     productRequestDtoMapper,
                             ResponseDtoMapper<ProductResponseDto, Product>
                                     productResponseDtoMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productRequestDtoMapper = productRequestDtoMapper;
        this.productResponseDtoMapper = productResponseDtoMapper;
    }

    @GetMapping
    public List<ProductResponseDto> getAll() {
        return parseResponseList(productService.getAll());

    }

    @GetMapping("/by-category/{id}")
    public List<ProductResponseDto> findByCategory(@PathVariable Long id) {
        return parseResponseList(productService.findByCategory(categoryService.get(id)));
    }

    @PostMapping("/add")
    public ProductResponseDto add(@RequestBody @Valid ProductRequestDto requestDto) {
        return productResponseDtoMapper.mapToDto(productService.add(
                productRequestDtoMapper.mapToModel(requestDto)));
    }

    @PutMapping("/update/{id}")
    public ProductResponseDto update(@RequestBody @Valid ProductRequestDto requestDto,
                                     @PathVariable Long id) {
        Product product = productRequestDtoMapper.mapToModel(requestDto);
        product.setId(id);
        return productResponseDtoMapper.mapToDto(productService.update(product));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        Product product = productService.get(id);
        if (!product.isDeleted()) {
            product.setDeleted(true);
            productService.update(product);
        }
    }

    private List<ProductResponseDto> parseResponseList(List<Product> products) {
        return products.stream()
                .filter(p -> !p.isDeleted())
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
