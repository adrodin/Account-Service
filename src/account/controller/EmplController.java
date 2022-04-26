package account.controller;



import account.model.user.User;
import account.service.PaymentService;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/empl/")
public class EmplController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;


    @GetMapping("payment")
    public ResponseEntity<?> getPayment(@RequestParam(value = "period", required = false) String period, @AuthenticationPrincipal User user){
        if(Objects.isNull(period)){
            return ResponseEntity.status(HttpStatus.OK).body(paymentService.getAllUserPayments(user));
        }

        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getUserPaymentByPeriod(user,period));
    }
}
