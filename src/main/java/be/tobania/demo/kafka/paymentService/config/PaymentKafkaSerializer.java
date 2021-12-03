package be.tobania.demo.kafka.paymentService.config;

import be.tobania.demo.kafka.paymentService.model.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class PaymentKafkaSerializer implements Serializer<Payment> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        log.info("enter");
    }

    @Override
    public byte[] serialize(String topic, Payment data) {

        if (data == null) {
            log.info("Data to serialized is null");
            return null;
        }
        try {
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing payment to byte[]");
        }
    }

    @Override
    public void close() {
    }
}