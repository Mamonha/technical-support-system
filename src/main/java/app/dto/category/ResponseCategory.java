package app.dto.category;

import app.entities.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCategory {
    private Long id;
    private String name;

    public ResponseCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ResponseCategory category(Category category) {
        return new ResponseCategory(
                category.getId(),
                category.getNome()
        );
    }
}
