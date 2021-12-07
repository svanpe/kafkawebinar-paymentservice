package be.tobania.demo.kafka.paymentService.service;

import be.tobania.demo.kafka.paymentService.config.OrderKafkaDeserializer;
import be.tobania.demo.kafka.paymentService.entities.OrderEntity;
import be.tobania.demo.kafka.paymentService.entities.PaymentEntity;
import be.tobania.demo.kafka.paymentService.entities.PaymentLineEntity;
import be.tobania.demo.kafka.paymentService.model.Order;
import be.tobania.demo.kafka.paymentService.model.OrderForPatch;
import be.tobania.demo.kafka.paymentService.model.Payment;
import be.tobania.demo.kafka.paymentService.model.PaymentLine;
import be.tobania.demo.kafka.paymentService.model.enums.OrderStatus;
import be.tobania.demo.kafka.paymentService.model.enums.PaymentStatus;
import be.tobania.demo.kafka.paymentService.repository.PaymentLineRepository;
import be.tobania.demo.kafka.paymentService.repository.PaymentRepository;
import be.tobania.demo.kafka.paymentService.service.mapper.PaymentApiEntityMapper;
import be.tobania.demo.kafka.paymentService.service.mapper.PaymentEntityApiMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private static final String ORDER_TOPIC = "orders";
    private static final String PAYMENT_TOPIC = "payments";

    public final PaymentRepository paymentRepository;
    public final PaymentLineRepository paymentLineRepository;

    public final ObjectMapper objectMapper;


    private final KafkaTemplate<String, Payment> kafkaTemplate;



    @Transactional
    public Payment createPayment(Payment payment) {

        final PaymentEntity paymentEntity = PaymentApiEntityMapper.mapOrder(payment);

        PaymentEntity addedPaymentEntity = paymentRepository.saveAndFlush(paymentEntity);

        Payment  addedPayment =  PaymentEntityApiMapper.mapPayment(addedPaymentEntity);

        //publishPayment(addedPayment);

        return addedPayment;
    }

    @Transactional
    public Payment createPaymentLine(Long paymentId, PaymentLine paymentLine) {

        final PaymentEntity paymentEntity = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("No payment found the given Id"));

        PaymentLineEntity paymentLineEntity = new PaymentLineEntity();

        paymentLineEntity.setAmount(paymentLine.getAmount());
        paymentLineEntity.setCommunication(paymentLine.getCommunication());
        paymentLineEntity.setCreationDate(paymentLine.getCreationDate());

        paymentEntity.getPaymentLineList().add(paymentLineEntity);

        //TODo: where to update the status of the order

        paymentEntity.setStatus(PaymentStatus.PAYED);

        PaymentEntity updatedPayment = paymentRepository.save(paymentEntity);

        Payment  paymentWithNewPayLine =  PaymentEntityApiMapper.mapPayment(updatedPayment);

        publishPayment(paymentWithNewPayLine);

        return paymentWithNewPayLine;
    }

    public List<Payment> getPaymentsByStatus(List<String> statuses){

        List<PaymentEntity> paymentEntities = new ArrayList<>();

        statuses.forEach(status -> paymentEntities.addAll(paymentRepository.findPaymentEntitiesByStatus(PaymentStatus.fromValue(status))));

        return paymentEntities.stream().map(PaymentEntityApiMapper::mapPayment).collect(Collectors.toList());
    }


    @Async
    public void publishPayment(Payment payment){

        log.info("start publishing payment");

        kafkaTemplate.send(PAYMENT_TOPIC, payment.getId().toString(), payment);

        log.info("payment published");

    }

    @KafkaListener(topics = ORDER_TOPIC, groupId = "payment-service")
    public void consume(Order order) throws IOException {
        log.info(String.format("#### -> Consumed new order with status-> %s", order.getStatus().name()));

        //TOD: refactor this part of the code to make more readable

        if(OrderStatus.PLACED == order.getStatus()){
            log.info("generate a new payment");

            Payment payment = new Payment();

            payment.setOrder(order);
            payment.setStatus(PaymentStatus.PLACED);
            payment.setCreationDate(LocalDate.now());

            Payment newPayment = this.createPayment(payment);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            String logPayment = objectMapper.writeValueAsString(newPayment);

            log.info(logPayment);

        }
    }

}
