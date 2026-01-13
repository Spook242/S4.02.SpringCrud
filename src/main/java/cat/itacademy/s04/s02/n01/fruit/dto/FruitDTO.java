package cat.itacademy.s04.s02.n01.fruit.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FruitDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 0, message = "The weight should be positive")
    private int weightInKilos;

    @NotNull(message = "The weight should be positive")
    private Long providerId;

    public FruitDTO() {
    }

    public FruitDTO(Long id, String name, int weightInKilos, Long providerId) {
        this.id = id;
        this.name = name;
        this.weightInKilos = weightInKilos;
        this.providerId = providerId;
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

    public Long getProviderId() {
        return providerId;
    }
    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
}