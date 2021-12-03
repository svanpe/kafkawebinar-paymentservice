package be.tobania.demo.kafka.paymentService.repository;

import be.tobania.demo.kafka.paymentService.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    public List<PaymentEntity> findPaymentEntitiesByStatus(String statusEnum);

}
