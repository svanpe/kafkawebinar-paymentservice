package be.tobania.demo.kafka.paymentService.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany( mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderList;

}