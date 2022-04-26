package account.controller;


import account.dto.payment.RequestPostPaymentDto;
import account.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/acct/")
public class AcctController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("payments")
    public ResponseEntity<?> addPayment(@RequestBody List<@Valid RequestPostPaymentDto> payments){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.addPayments(payments));
    }

    @PutMapping("payments")
    public ResponseEntity<?> editPayment(@RequestBody @Valid RequestPostPaymentDto payment){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.editPayment(payment));
    }



}
