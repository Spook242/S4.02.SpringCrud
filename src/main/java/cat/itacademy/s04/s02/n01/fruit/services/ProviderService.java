package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.dto.ProviderDTO;
import java.util.List;

public interface ProviderService {
    ProviderDTO save(ProviderDTO providerDTO);

    ProviderDTO update(ProviderDTO providerDTO);
    void delete(Long id);
    ProviderDTO getOne(Long id);
    List<ProviderDTO> getAll();
}