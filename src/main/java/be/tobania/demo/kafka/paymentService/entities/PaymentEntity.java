package be.tobania.demo.kafka.paymentService.entities;

import be.tobania.demo.kafka.paymentService.model.enums.PaymentStatus;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private LocalDate creationDate;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderEntity order;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PaymentLineEntity> paymentLineList;

}
