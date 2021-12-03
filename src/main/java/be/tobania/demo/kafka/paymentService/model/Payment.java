package be.tobania.demo.kafka.paymentService.model;

import be.tobania.demo.kafka.paymentService.model.enums.PaymentStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Validated
@ApiModel("Contain payment date")
@ToString
@Getter
public class Payment {
    private Long id;

    private LocalDate creationDate;

    private Order order;

    private PaymentStatus status;

    private List<PaymentLine> paymentLineList;

}

