package app.dto.response;

import app.entities.Response;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResponse {

    @NotBlank(message = "Description is mandatory")
    @Size(max = 700, message ="Maximum characters 700" )
    private String description;

    @NotNull
    private Long userId;

    @NotNull
    private Long ticketId;

    public Response response(){
        Response response= new Response();
        response.setDescription(this.description);
        return response;
    }
}
