package app.dto.category;

import app.entities.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCategory {
    private Long id;
    private String name;
    private String description;


    public ResponseCategory(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description=description;

    }

    public static ResponseCategory category(Category category) {
        return new ResponseCategory(
                category.getId(),
                category.getNome(),
                category.getDescription()
        );
    }
}
