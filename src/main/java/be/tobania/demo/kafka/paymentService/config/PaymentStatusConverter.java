package be.tobania.demo.kafka.paymentService.config;

import be.tobania.demo.kafka.paymentService.model.enums.PaymentStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;


public class PaymentStatusConverter implements Converter<String, PaymentStatus> {
    @Override
    public PaymentStatus convert(String input) {
        return PaymentStatus.valueOf(input.toUpperCase());
    }
}
