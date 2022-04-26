package account.service;


import account.dto.payment.RequestPostPaymentDto;
import account.dto.payment.ResponseGetPaymentDto;
import account.dto.payment.ResponsePostPaymentDto;
import account.exception.*;
import account.mapper.PaymentMappper;
import account.model.Payment;
import account.model.user.User;
import account.repository.PaymentRepository;
import account.repository.UserRepository;
import account.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentMappper paymentMappper;

  //  @Transactional
    public ResponsePostPaymentDto addPayments(List<RequestPostPaymentDto> payments){
        if(payments.stream().anyMatch(requestPostPaymentDto -> requestPostPaymentDto.getSalary() < 0)){
            throw new WrongSalaryException();
        }

        for (RequestPostPaymentDto payment: payments) {

            LocalDate date = DateUtil.stringToDate(payment.getPeriod());
            var testUser = userRepository.findByUsername(payment.getUsername());

            if(testUser.isEmpty()){
                throw new UserNoExistsException();
            }

            User user = testUser.get();

            if(paymentRepository.findPaymentsByPeriodAndUser(date,user).isPresent()){
                throw new PaymentAlreadyExistsException();
            }

            Payment paymentToSave = PaymentMappper.requestPostPaymentDtoToPayment(payment,date,user);
            paymentRepository.save(paymentToSave);
        }
        return  new ResponsePostPaymentDto();
    }

    public List<?> getAllUserPayments(User user){
        return paymentRepository.findPaymentsByUserOrderByPeriodDesc(user).stream()
                .map(payment -> PaymentMappper.paymentToResponseGetPaymentDto(payment,user)
                )
                .collect(Collectors.toList());
    }

    public ResponseGetPaymentDto getUserPaymentByPeriod(User user, String period){
        LocalDate date = DateUtil.stringToDate(period);
        var payment =  paymentRepository.findPaymentsByPeriodAndUser(date,user).orElseThrow(PaymentNoExistsException::new);
        return PaymentMappper.paymentToResponseGetPaymentDto(payment,user);
    }

    public ResponsePostPaymentDto editPayment(RequestPostPaymentDto RPDT){
        LocalDate date = DateUtil.stringToDate(RPDT.getPeriod());
        User user = userRepository.findByUsername(RPDT.getUsername()).orElseThrow(UserNoExistsException::new);
        Payment payment = paymentRepository.findPaymentsByPeriodAndUser(date,user).orElseThrow(PaymentNoExistsException::new);
        payment.setSalary(RPDT.getSalary());
        paymentRepository.save(payment);
        return ResponsePostPaymentDto.builder().status("Updated successfully!").build();
    }




}
