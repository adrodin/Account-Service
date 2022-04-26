package account.dto.user;


import account.validator.BreachedPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestChangePasswordDto {


    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 12, max = 100, message = "Password length must be 12 chars minimum!")
    @BreachedPassword(message = "The password is in the hacker's database!")
    @JsonProperty(value="new_password")
    private String password;
}
