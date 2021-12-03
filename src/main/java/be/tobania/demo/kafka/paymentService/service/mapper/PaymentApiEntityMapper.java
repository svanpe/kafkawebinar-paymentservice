package be.tobania.demo.kafka.paymentService.service.mapper;

import be.tobania.demo.kafka.paymentService.entities.*;
import be.tobania.demo.kafka.paymentService.model.Customer;
import be.tobania.demo.kafka.paymentService.model.Payment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class PaymentApiEntityMapper {


    public static PaymentEntity mapOrder(Payment payment) {

        CustomerEntity customer = new CustomerEntity();
        final Customer apiCustomer = payment.getOrder().getCustomer();
        customer.setEmail(apiCustomer.getEmail());
        customer.setFirstName(apiCustomer.getFirstName());
        customer.setLastName(apiCustomer.getLastName());

        List<OrderItemEntity> orderItemList = payment.getOrder().getOrderItems().stream().map(item -> {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setQuantity(item.getQuantity());

            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(item.getProduct().getName());
            productEntity.setDescription(item.getProduct().getDescription());

            orderItem.setProductEntity(productEntity);
            return orderItem;
        }).collect(Collectors.toList());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCreationDate(payment.getOrder().getCreationDate());
        orderEntity.setCustomerEntity(customer);
        orderEntity.setStatus(payment.getOrder().getStatus().getValue());
        orderEntity.setOrderItems(orderItemList);

        List<PaymentLineEntity> paymentLineEntities = payment.getPaymentLineList().stream().map(paymentLine -> {

            PaymentLineEntity paymentLineEntity = new PaymentLineEntity();

            paymentLineEntity.setAmount(paymentLine.getAmount());
            paymentLineEntity.setCommunication(paymentLine.getCommunication());
            paymentLineEntity.setCreationDate(paymentLine.getCreationDate());

            return paymentLineEntity;
        }).collect(Collectors.toList());

        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setCreationDate(payment.getCreationDate());
        paymentEntity.setOrder(orderEntity);
        paymentEntity.setStatus(payment.getStatus());
        paymentEntity.setPaymentLineList(paymentLineEntities);

        return paymentEntity;

    }


}
