package account.mapper;


import account.dto.payment.RequestPostPaymentDto;
import account.dto.payment.ResponseGetPaymentDto;
import account.model.Payment;
import account.model.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public final class PaymentMappper {

    public static Payment requestPostPaymentDtoToPayment(RequestPostPaymentDto RPPD, LocalDate date, User user){

        Payment payment = new Payment();
        payment.setSalary(RPPD.getSalary());
        payment.setPeriod(date);
        payment.setUser(user);
        return payment;
    }

    public static ResponseGetPaymentDto paymentToResponseGetPaymentDto(Payment payment, User user){

        return ResponseGetPaymentDto.builder()
                .name(user.getName())
                .period(DateTimeFormatter.ofPattern("MMMM-yyyy").format(payment.getPeriod()))
                .salary(payment.getSalary()/100 + " dollar(s) " + payment.getSalary()%100 + " cent(s)" )
                .lastname(user.getLastName()).build();

    }
}
