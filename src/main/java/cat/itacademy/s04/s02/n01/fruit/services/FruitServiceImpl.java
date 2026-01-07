package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.springframework.stereotype.Service;

@Service // Importante: Esto le dice a Spring que esta clase es un Servicio
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;

    // Inyección de dependencia por constructor (Mejor práctica que @Autowired)
    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Override
    public Fruit save(Fruit fruit) {
        return fruitRepository.save(fruit);
    }
}
