package be.tobania.demo.kafka.paymentService.config;

import be.tobania.demo.kafka.paymentService.model.enums.OrderStatus;
import org.springframework.core.convert.converter.Converter;

public class OrderStatusConverter implements Converter<String, OrderStatus> {
    @Override
    public OrderStatus convert(String input) {
        return OrderStatus.fromValue(input.toUpperCase());
    }
}