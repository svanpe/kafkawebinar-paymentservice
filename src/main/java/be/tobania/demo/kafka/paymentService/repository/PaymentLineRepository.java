package be.tobania.demo.kafka.paymentService.repository;

import be.tobania.demo.kafka.paymentService.entities.PaymentLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentLineRepository extends JpaRepository<PaymentLineEntity, Long> {
}
