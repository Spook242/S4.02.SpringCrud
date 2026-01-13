package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;
    private final ProviderRepository providerRepository;

    public FruitServiceImpl(FruitRepository fruitRepository, ProviderRepository providerRepository) {
        this.fruitRepository = fruitRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public FruitDTO save(FruitDTO fruitDTO) {
        Provider provider = providerRepository.findById(fruitDTO.getProviderId())
                .orElseThrow(() -> new FruitNotFoundException("The supplier could not be found with id: " + fruitDTO.getProviderId()));

        Fruit fruit = mapToEntity(fruitDTO, provider);

        Fruit savedFruit = fruitRepository.save(fruit);

        return mapToDTO(savedFruit);
    }

    @Override
    public FruitDTO update(FruitDTO fruitDTO) {
        Fruit fruit = fruitRepository.findById(fruitDTO.getId())
                .orElseThrow(() -> new FruitNotFoundException("The supplier could not be found with id: " + fruitDTO.getId()));

        Provider provider = providerRepository.findById(fruitDTO.getProviderId())
                .orElseThrow(() -> new FruitNotFoundException("The supplier could not be found with id: " + fruitDTO.getProviderId()));

        fruit.setName(fruitDTO.getName());
        fruit.setWeightInKilos(fruitDTO.getWeightInKilos());
        fruit.setProvider(provider);

        Fruit updatedFruit = fruitRepository.save(fruit);

        return mapToDTO(updatedFruit);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void delete(int id) {
        if (!fruitRepository.existsById((long) id)) {
            throw new FruitNotFoundException("The fruit has not been found with id: " + id);
        }
        fruitRepository.deleteById((long) id);
    }

    @Override
    public FruitDTO getOne(int id) {
        Fruit fruit = fruitRepository.findById((long) id)
                .orElseThrow(() -> new FruitNotFoundException("The fruit has not been found with id: " + id));
        return mapToDTO(fruit);
    }

    @Override
    public List<FruitDTO> getAll(Long providerId) {
        List<Fruit> fruits;

        if (providerId != null) {
            fruits = fruitRepository.findByProviderId(providerId);
        } else {
            fruits = fruitRepository.findAll();
        }

        return fruits.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FruitDTO getOne(Long id) {
        return null;
    }

    private FruitDTO mapToDTO(Fruit fruit) {
        return new FruitDTO(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos(),
                fruit.getProvider().getId()
        );
    }

    private Fruit mapToEntity(FruitDTO dto, Provider provider) {
        return new Fruit(
                dto.getId(),
                dto.getName(),
                dto.getWeightInKilos(),
                provider
        );
    }
}