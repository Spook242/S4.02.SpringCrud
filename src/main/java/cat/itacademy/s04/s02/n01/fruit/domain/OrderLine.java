package cat.itacademy.s04.s02.n01.fruit.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderLine {

    private String fruitName;
    private int quantity;

    public OrderLine() {}

    public OrderLine(String fruitName, int quantity) {
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

}