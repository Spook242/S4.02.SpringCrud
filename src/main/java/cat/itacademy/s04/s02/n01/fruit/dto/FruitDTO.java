package cat.itacademy.s04.s02.n01.fruit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FruitDTO {

    private Long id;

    @NotBlank(message = "The name cannot be empty.")
    private String name;

    @NotNull(message = "Weight is mandatory")
    @Positive(message = "The weight must be greater than 0")
    private int weightInKilos;

    public FruitDTO() {}

    public FruitDTO(Long id, String name, int weightInKilos) {
        this.id = id;
        this.name = name;
        this.weightInKilos = weightInKilos;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getWeightInKilos() {
        return weightInKilos;
    }
    public void setWeightInKilos(int weightInKilos) {
        this.weightInKilos = weightInKilos;
    }
}