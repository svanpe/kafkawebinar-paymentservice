package be.tobania.demo.kafka.paymentService.service.mapper;


import be.tobania.demo.kafka.paymentService.entities.OrderEntity;
import be.tobania.demo.kafka.paymentService.entities.PaymentEntity;
import be.tobania.demo.kafka.paymentService.model.*;
import be.tobania.demo.kafka.paymentService.model.enums.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentEntityApiMapper {

    public static Order mapOrder(OrderEntity order) {

        Customer customer = new Customer();
        customer.setId(order.getCustomerEntity().getId());
        customer.setEmail(order.getCustomerEntity().getEmail());
        customer.setFirstName(order.getCustomerEntity().getFirstName());
        customer.setLastName(order.getCustomerEntity().getLastName());

        List<OrderItem> orderItemList = order.getOrderItems().stream().map(item -> {
            OrderItem orderItem = new OrderItem();

            orderItem.setId(item.getId());
            orderItem.setQuantity(item.getQuantity());

            Product product = new Product();

            product.setId(item.getProductEntity().getId());
            product.setDescription(item.getProductEntity().getName());
            product.setName(item.getProductEntity().getDescription());

            orderItem.setProduct(product);
            return orderItem;
        }).collect(Collectors.toList());


        Order orderApi = new Order();
        orderApi.setId(order.getId());
        orderApi.setCreationDate(order.getCreationDate());
        orderApi.setCustomer(customer);
        orderApi.setStatus(OrderStatus.fromValue(order.getStatus()));
        orderApi.setOrderItems(orderItemList);

        return orderApi;

    }

    public static Payment mapPayment(PaymentEntity paymentEntity) {
        Payment payment = new Payment();

        if(paymentEntity.getPaymentLineList()!=null && !paymentEntity.getPaymentLineList().isEmpty()){
        List<PaymentLine> paymentLines = paymentEntity.getPaymentLineList().stream().map(paymentLineEntity -> {
            PaymentLine paymentLine = new PaymentLine();
            paymentLine.setAmount(paymentLineEntity.getAmount());
            paymentLine.setCommunication(paymentLineEntity.getCommunication());
            paymentLine.setCreationDate(paymentLineEntity.getCreationDate());
            paymentLine.setId(paymentLineEntity.getId());
            return paymentLine;
        }).collect(Collectors.toList());

            payment.setPaymentLineList(paymentLines);
        }


            payment.setCreationDate(paymentEntity.getCreationDate());
        payment.setOrder(mapOrder(paymentEntity.getOrder()));
        payment.setId(paymentEntity.getId());
        payment.setStatus(paymentEntity.getStatus());

        return payment;
    }

}
