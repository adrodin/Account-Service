package account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Payment Already Exists!")
public class PaymentAlreadyExistsException extends RuntimeException{
    public PaymentAlreadyExistsException() {
        super();
    }
}

