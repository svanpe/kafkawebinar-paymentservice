package be.tobania.demo.kafka.paymentService.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private LocalDate creationDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CustomerEntity customerEntity;

    private String status;

    @OneToMany(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems;

}