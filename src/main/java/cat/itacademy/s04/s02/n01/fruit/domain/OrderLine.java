package cat.itacademy.s04.s02.n01.fruit.domain;

public class OrderLine {

    private String fruitName;
    private int quantity;

    public OrderLine() {}

    public OrderLine(String fruitName, int quantity) {
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public String getFruitName() {
        return fruitName;
    }
    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}