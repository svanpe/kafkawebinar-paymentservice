package be.tobania.demo.kafka.paymentService.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentLineEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String communication;

    private LocalDate creationDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PaymentEntity paymentEntity;

}