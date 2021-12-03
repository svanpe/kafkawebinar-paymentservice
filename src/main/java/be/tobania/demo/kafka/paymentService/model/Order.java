package be.tobania.demo.kafka.paymentService.model;

import be.tobania.demo.kafka.paymentService.model.enums.OrderStatus;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Validated
@ApiModel("Contain order data")
@ToString
public class Order {

    private Long id;

    private LocalDate creationDate;

    private Customer customer;

    private OrderStatus status;

    private List<OrderItem> orderItems;
}
