package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "fruits")
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "weight_in_kilos")
    private int weightInKilos;

    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    public Fruit() {}

    public Fruit(Long id, String name, int weightInKilos, Provider provider) {
        this.id = id;
        this.name = name;
        this.weightInKilos = weightInKilos;
        this.provider = provider;
    }

}