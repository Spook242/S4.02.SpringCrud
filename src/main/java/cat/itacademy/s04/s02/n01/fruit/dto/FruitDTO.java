package cat.itacademy.s04.s02.n01.fruit.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}