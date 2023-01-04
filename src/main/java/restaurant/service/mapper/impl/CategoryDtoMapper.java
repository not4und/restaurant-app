package restaurant.service.mapper.impl;

import org.springframework.stereotype.Component;
import restaurant.dto.request.CategoryRequestDto;
import restaurant.dto.response.CategoryResponseDto;
import restaurant.model.Category;
import restaurant.service.mapper.RequestDtoMapper;
import restaurant.service.mapper.ResponseDtoMapper;

@Component
public class CategoryDtoMapper implements RequestDtoMapper<CategoryRequestDto, Category>,
        ResponseDtoMapper<CategoryResponseDto, Category> {
    @Override
    public Category mapToModel(CategoryRequestDto dto) {
        return new Category(dto.getName());
    }

    @Override
    public CategoryResponseDto mapToDto(Category category) {
        return new CategoryResponseDto(category.getId(), category.getName());
    }
}
