package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fruits")
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required.")
    private String name;

    @Column(name = "weight_in_kilos", nullable = false)
    @Min(value = 0, message = "Weight cannot be negative.")
    private int weightInKilos;
}
