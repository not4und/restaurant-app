package restaurant.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.dto.request.CategoryRequestDto;
import restaurant.dto.response.CategoryResponseDto;
import restaurant.model.Category;
import restaurant.service.CategoryService;
import restaurant.service.mapper.RequestDtoMapper;
import restaurant.service.mapper.ResponseDtoMapper;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final RequestDtoMapper<CategoryRequestDto, Category> categoryRequestDtoMapper;
    private final ResponseDtoMapper<CategoryResponseDto, Category> categoryResponseDtoMapper;

    public CategoryController(CategoryService categoryService,
                              RequestDtoMapper<CategoryRequestDto, Category>
                                      categoryRequestDtoMapper,
                              ResponseDtoMapper<CategoryResponseDto, Category>
                                      categoryResponseDtoMapper) {
        this.categoryService = categoryService;
        this.categoryRequestDtoMapper = categoryRequestDtoMapper;
        this.categoryResponseDtoMapper = categoryResponseDtoMapper;
    }

    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return categoryService.getAll().stream()
                .map(categoryResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public CategoryResponseDto add(@RequestBody @Valid CategoryRequestDto requestDto) {
        return categoryResponseDtoMapper.mapToDto(categoryService
                .add(categoryRequestDtoMapper.mapToModel(requestDto)));
    }

    @PutMapping ("/update/{id}")
    public CategoryResponseDto update(@RequestBody @Valid CategoryRequestDto requestDto,
                                      @PathVariable Long id) {
        Category category = categoryRequestDtoMapper.mapToModel(requestDto);
        category.setId(id);
        return categoryResponseDtoMapper.mapToDto(categoryService.update(category));
    }
}
