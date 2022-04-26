package account.repository;

import account.model.Payment;
import account.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findPaymentsByPeriodAndUser(LocalDate period, User user);
    List<Payment> findPaymentsByUserOrderByPeriodDesc(User user);
}
