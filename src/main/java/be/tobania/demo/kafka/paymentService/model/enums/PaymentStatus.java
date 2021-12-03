package be.tobania.demo.kafka.paymentService.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
public enum PaymentStatus {
    PLACED("placed"),

    ONGOING("ongoing"),

    PAYED("payed");

    private String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public static PaymentStatus fromValue(String value) {
        for (PaymentStatus b : PaymentStatus.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

}
