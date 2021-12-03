package be.tobania.demo.kafka.paymentService.service;

import be.tobania.demo.kafka.paymentService.entities.OrderEntity;
import be.tobania.demo.kafka.paymentService.entities.PaymentEntity;
import be.tobania.demo.kafka.paymentService.entities.PaymentLineEntity;
import be.tobania.demo.kafka.paymentService.model.Order;
import be.tobania.demo.kafka.paymentService.model.OrderForPatch;
import be.tobania.demo.kafka.paymentService.model.Payment;
import be.tobania.demo.kafka.paymentService.model.PaymentLine;
import be.tobania.demo.kafka.paymentService.model.enums.OrderStatus;
import be.tobania.demo.kafka.paymentService.repository.PaymentLineRepository;
import be.tobania.demo.kafka.paymentService.repository.PaymentRepository;
import be.tobania.demo.kafka.paymentService.service.mapper.PaymentApiEntityMapper;
import be.tobania.demo.kafka.paymentService.service.mapper.PaymentEntityApiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private static final String ORDER_TOPIC = "orders";

    public final PaymentRepository paymentRepository;
    public final PaymentLineRepository paymentLineRepository;


    private final KafkaTemplate<String, Payment> kafkaTemplate;



    @Transactional
    public Payment createPayment(Payment payment) {

        final PaymentEntity paymentEntity = PaymentApiEntityMapper.mapOrder(payment);

        PaymentEntity addedPaymentEntity = paymentRepository.saveAndFlush(paymentEntity);

        Payment  addedPayment =  PaymentEntityApiMapper.mapPayment(addedPaymentEntity);

        publishPayment(addedPayment);

        return addedPayment;
    }

    @Transactional
    public PaymentLine createPaymentLine(Long paymentId, PaymentLine paymentLine) {

        final PaymentEntity paymentEntity = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("No payment found the given Id"));

        PaymentLineEntity paymentLineEntity = new PaymentLineEntity();

        paymentLineEntity.setAmount(paymentLine.getAmount());
        paymentLineEntity.setCommunication(paymentLine.getCommunication());
        paymentLineEntity.setCreationDate(paymentLine.getCreationDate());

        paymentEntity.getPaymentLineList().add(paymentLineEntity);

        paymentLineEntity.setPaymentEntity(paymentEntity);


        PaymentLineEntity addedPaymentEntity = paymentLineRepository.save(paymentLineEntity);

        PaymentLine line = new PaymentLine();
        line.setId(addedPaymentEntity.getId());
        line.setCreationDate(line.getCreationDate());
        line.setCommunication(line.getCommunication());
        line.setAmount(addedPaymentEntity.getAmount());

        final PaymentEntity updatePaymentEntity = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("No payment found the given Id"));

        Payment  updatePayment =  PaymentEntityApiMapper.mapPayment(updatePaymentEntity);

        publishPayment(updatePayment);

        return line;
    }


    @Async
    public void publishPayment(Payment payment){

        log.info("start publishing payment");

        kafkaTemplate.send(ORDER_TOPIC, payment.getId().toString(), payment);

        log.info("order published");

    }

}
