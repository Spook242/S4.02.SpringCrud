package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.dto.ProviderDTO;
import cat.itacademy.s04.s02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public ProviderDTO save(ProviderDTO providerDTO) {
        if (providerRepository.existsByName(providerDTO.getName())) {
            throw new RuntimeException("There is already a provider with the name: " + providerDTO.getName());
        }

        Provider provider = mapToEntity(providerDTO);

        Provider savedProvider = providerRepository.save(provider);

        return mapToDTO(savedProvider);
    }

    private Provider mapToEntity(ProviderDTO dto) {
        return new Provider(dto.getId(), dto.getName(), dto.getCountry());
    }

    private ProviderDTO mapToDTO(Provider entity) {
        return new ProviderDTO(entity.getId(), entity.getName(), entity.getCountry());
    }
}