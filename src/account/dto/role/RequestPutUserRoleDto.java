package account.dto.role;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestPutUserRoleDto {
    @NotNull(message = "User cannot be null")
    @NotBlank(message = "User cannot be blank")
    private String user;
    @NotNull(message = "Role cannot be null")
    @NotBlank(message = "Role cannot be blank")
    private String role;
    private Operation operation;
}
