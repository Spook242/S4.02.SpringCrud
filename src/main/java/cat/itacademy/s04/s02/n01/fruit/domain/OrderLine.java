package cat.itacademy.s04.s02.n01.fruit.domain;

public class OrderLine {

    private String fruitName;
    private int quantity;

    // 1. Constructor buit (IMPTE per a MongoDB/Jackson)
    public OrderLine() {}

    // 2. Constructor ple
    public OrderLine(String fruitName, int quantity) {
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    // 3. Getters (IMPTE: Jackson els necessita per enviar el JSON)
    public String getFruitName() { return fruitName; }
    public void setFruitName(String fruitName) { this.fruitName = fruitName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}