package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Override
    public FruitDTO save(FruitDTO fruitDTO) {
        Fruit entity = mapToEntity(fruitDTO);
        Fruit savedEntity = fruitRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public FruitDTO update(FruitDTO fruitDTO) {
        if (fruitDTO.getId() == null || !fruitRepository.existsById(fruitDTO.getId())) {
            throw new FruitNotFoundException("Unable to update. ID not found: " + fruitDTO.getId());
        }
        Fruit entity = mapToEntity(fruitDTO);
        Fruit savedEntity = fruitRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public void delete(Long id) {
        if (!fruitRepository.existsById(id)) {
            throw new FruitNotFoundException("Cannot be deleted. ID not found: " + id);
        }
        fruitRepository.deleteById(id);
    }

    @Override
    public FruitDTO getOne(Long id) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new FruitNotFoundException("Fruit not found: " + id));
        return mapToDTO(fruit);
    }

    @Override
    public List<FruitDTO> getAll() {
        return fruitRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private FruitDTO mapToDTO(Fruit fruit) {
        return new FruitDTO(fruit.getId(), fruit.getName(), fruit.getWeightInKilos());
    }

    private Fruit mapToEntity(FruitDTO fruitDTO) {
        return new Fruit(fruitDTO.getId(), fruitDTO.getName(), fruitDTO.getWeightInKilos());
    }
}