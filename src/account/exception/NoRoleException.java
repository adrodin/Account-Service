package account.exception;




import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "The user does not have a role!")
public class NoRoleException extends RuntimeException{
    public NoRoleException() {
        super();
    }
}



