package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.dto.ProviderDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ProviderDTO update(ProviderDTO providerDTO) {
        Provider provider = providerRepository.findById(providerDTO.getId())
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + providerDTO.getId()));

        provider.setName(providerDTO.getName());
        provider.setCountry(providerDTO.getCountry());

        return mapToDTO(providerRepository.save(provider));
    }

    @Override
    public void delete(Long id) {
        if (!providerRepository.existsById(id)) {
            throw new RuntimeException("Provider not found with id: " + id);
        }
        providerRepository.deleteById(id);
    }

    @Override
    public ProviderDTO getOne(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + id));
        return mapToDTO(provider);
    }

    @Override
    public List<ProviderDTO> getAll() {
        return providerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Provider mapToEntity(ProviderDTO dto) {
        return new Provider(dto.getId(), dto.getName(), dto.getCountry());
    }

    private ProviderDTO mapToDTO(Provider entity) {
        return new ProviderDTO(entity.getId(), entity.getName(), entity.getCountry());
    }
}