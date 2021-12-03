package be.tobania.demo.kafka.paymentService.config;

import be.tobania.demo.kafka.paymentService.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class OrderKafkaDeserializer implements Deserializer<Order> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Order deserialize(String s, byte[] data) {
        try {
            if (data == null) {
                log.info("Data to serialized is null");
                return null;
            }
            log.info("Serializing...");
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(new String(data), Order.class);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing order to byte[]");
        }    }


    @Override
    public void close() {
    }
}