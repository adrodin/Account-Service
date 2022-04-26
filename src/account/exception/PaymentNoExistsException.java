package account.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Payment No Exists!")
public class PaymentNoExistsException extends RuntimeException{
    public PaymentNoExistsException() {
        super();
    }
}

