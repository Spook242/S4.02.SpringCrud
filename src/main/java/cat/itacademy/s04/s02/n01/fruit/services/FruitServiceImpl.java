package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Override
    public Fruit save(Fruit fruit) {
        return fruitRepository.save(fruit);
    }

    @Override
    public List<Fruit> getAll() {
        return List.of();
    }

    @Override
    public Fruit getOne(Long id) {
        return fruitRepository.findById(id)
                .orElseThrow(() -> new FruitNotFoundException("Fruit not found with id: " + id));
    }

    @Override
    public Fruit update(Fruit fruit) {
        if (fruit.getId() == null || !fruitRepository.existsById(fruit.getId())) {
            throw new FruitNotFoundException("Cannot be updated. Fruit not found with id: " + fruit.getId());
        }

        return fruitRepository.save(fruit);
    }

    @Override
    public void delete(Long id) {
        if (!fruitRepository.existsById(id)) {
            throw new FruitNotFoundException("Cannot be removed. Fruit not found with id: " + id);
        }
        fruitRepository.deleteById(id);
    }

}
