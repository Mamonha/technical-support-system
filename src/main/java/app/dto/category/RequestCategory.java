package app.dto.category;

import app.entities.Category;
import app.entities.Response;
import app.entities.Ticket;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RequestCategory {

    @NotBlank(message = "name is mandatory")
    @NotNull (message = "name is Null")
    private String name;

    private String description;

    public Category category(){
        Category category = new Category();
        category.setDescription(this.description);
        category.setNome(this.name);
        return category;
    }

}
