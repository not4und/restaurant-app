package restaurant.dto.request;

import javax.validation.constraints.NotBlank;

public class CategoryRequestDto {
    @NotBlank(message = "Category name can't be null or blank.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
