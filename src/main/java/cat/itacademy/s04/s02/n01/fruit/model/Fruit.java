package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.*;

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

    public Provider getProvider() {
        return provider;
    }
    public void setProvider(Provider provider) {
        this.provider = provider;
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