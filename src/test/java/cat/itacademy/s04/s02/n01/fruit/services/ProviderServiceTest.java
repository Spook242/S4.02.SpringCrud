package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.dto.ProviderDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.sql.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProviderServiceTest {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderServiceImpl providerService;

    @Test
    void save_ShouldReturnDto_WhenNameIsUnique() {
        ProviderDTO dto = new ProviderDTO(null, "Pujol´s Fruit", "Catalonia");
        Provider entity = new Provider(1L, "Pujol´s Fruit", "Catalonia");

        when(providerRepository.existsByName("Pujol´s Fruit")).thenReturn(false);
        when(providerRepository.save(any(Provider.class))).thenReturn(entity);

        ProviderDTO result = providerService.save(dto);

        assertEquals(1L, result.getId());
        verify(providerRepository).save(any(Provider.class));
    }

    @Test
    void save_ShouldThrowException_WhenNameExists() {
        ProviderDTO dto = new ProviderDTO(null, "Repeated Fruits", "Catalonia");
        when(providerRepository.existsByName("Repeated Fruits")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> providerService.save(dto));
        verify(providerRepository, never()).save(any(Provider.class));
    }
}