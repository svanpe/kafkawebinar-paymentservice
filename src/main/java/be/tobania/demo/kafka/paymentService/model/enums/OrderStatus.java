package be.tobania.demo.kafka.paymentService.model.enums;


import lombok.Getter;

@Getter
public enum OrderStatus {

    PLACED("placed"),

    PAYED("payed"),

    SHIPPED("shipped"),

    DELIVERED("delivered");

    private String value;

private OrderStatus(String value){
    this.value = value;
}

    public static OrderStatus fromValue(String text) {
        for (OrderStatus b : values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

}