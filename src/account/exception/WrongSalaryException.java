package account.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Wrong salary in payment list")
public class WrongSalaryException extends RuntimeException{
    public WrongSalaryException() {
        super();
    }
}


