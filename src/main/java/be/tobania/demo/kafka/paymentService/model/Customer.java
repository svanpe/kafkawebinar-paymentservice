package be.tobania.demo.kafka.paymentService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Validated
@ApiModel("customer data")
public class Customer   {

  private Long id;

  private String firstName;

  private String lastName;

  private String email;

}
