package account.dto.payment;


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
public class RequestPostPaymentDto {

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @JsonProperty(value = "employee")
    @Email(message = "Employee invalid format")
    @Pattern(regexp = ".+@acme.com", message = "Employee invalid format")
    private String username;

    @NotNull(message = "Period cannot be null")
    @NotBlank(message = "Period cannot be blank")
    @Pattern(regexp = "(0?[1-9]|1[0-2])-\\d+", message = "Period invalid format")
    private String period;

    @NotNull(message = "Salary cannot be null")
    @PositiveOrZero(message = "Salary cannot be negative")
    private Long salary;
}
