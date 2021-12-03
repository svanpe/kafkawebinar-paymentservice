package be.tobania.demo.kafka.paymentService.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Validated
@ApiModel("Contain payment date")
@ToString
public class PaymentLine {

    private Long id;

    private BigDecimal amount;

    private String communication;

    private LocalDate creationDate;

}

