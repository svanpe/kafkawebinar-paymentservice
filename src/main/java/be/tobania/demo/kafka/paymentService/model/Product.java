package be.tobania.demo.kafka.paymentService.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@NoArgsConstructor
public class Product {

    private Long id;

    private String name;

    private String description;

}
