package be.tobania.demo.kafka.paymentService.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Validated
@Getter
@Setter
@NoArgsConstructor
@ApiModel("contain data for one order ")
public class OrderItem {

    private Long id;

    private Product product;

    private Integer quantity;

}
