package cat.itacademy.s04.s02.n01.fruit.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String clientName;
    private LocalDate deliveryDate;

    private List<OrderLine> orderLines;

    public Order() {}

    public Order(String clientName, LocalDate deliveryDate, List<OrderLine> orderLines) {
        this.clientName = clientName;
        this.deliveryDate = deliveryDate;
        this.orderLines = orderLines;
    }

}