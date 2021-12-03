package be.tobania.demo.kafka.paymentService.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    private String firstName ;
    private String lastName ;
    private String email;
    private String password;

   @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.ALL)
   private List<OrderEntity> orders;

}