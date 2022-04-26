package account.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseGetUserDto {

    private Long id;
    private String name;
    private String lastname;
    private String email;
    private List<String> roles;

}
