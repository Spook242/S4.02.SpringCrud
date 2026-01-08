package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;

import java.util.List;

public interface FruitService {

    Fruit save(Fruit fruit);

    List<Fruit> getAll();

    Fruit getOne(Long id);

    Fruit update(Fruit fruit);

    void delete(Long id);
}


