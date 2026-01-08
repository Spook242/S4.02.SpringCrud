package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;

import java.util.List;

public interface FruitService {

    FruitDTO save(FruitDTO fruitDTO);

    List<FruitDTO> getAll();

    FruitDTO getOne(Long id);

    FruitDTO update(FruitDTO fruit);

    void delete(Long id);
}


