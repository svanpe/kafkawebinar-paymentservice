package be.tobania.demo.kafka.paymentService.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi OpenApi() {
        return GroupedOpenApi.builder()
                .group("payment-service")
                .packagesToScan("be.tobania.demo.kafka.paymentService")
                .build();
    }


}