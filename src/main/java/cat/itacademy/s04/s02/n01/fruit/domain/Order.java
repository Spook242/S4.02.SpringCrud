package cat.itacademy.s04.s02.n01.fruit.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String clientName;
    private LocalDate deliveryDate;

    // IMPORTANT: La llista de línies de comanda
    private List<OrderLine> orderLines;

    // 1. Constructor buit
    public Order() {}

    // 2. Constructor ple
    public Order(String clientName, LocalDate deliveryDate, List<OrderLine> orderLines) {
        this.clientName = clientName;
        this.deliveryDate = deliveryDate;
        this.orderLines = orderLines;
    }

    // 3. GETTERS (Si en falta un, ERROR 500)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }

    public List<OrderLine> getOrderLines() { return orderLines; }
    public void setOrderLines(List<OrderLine> orderLines) { this.orderLines = orderLines; }
}