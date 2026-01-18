package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.sql.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.sql.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FruitServiceTest {

    @Mock
    private FruitRepository fruitRepository;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private FruitServiceImpl fruitService;


    @Test
    void save_ShouldReturnDto_WhenProviderExists() {
        Long providerId = 10L;
        Provider mockProvider = new Provider(providerId, "Fruits SL", "Catalonia");

        FruitDTO inputDto = new FruitDTO(null, "Strawberry", 5, providerId);
        Fruit savedEntity = new Fruit(1L, "Strawberry", 5, mockProvider);

        when(providerRepository.findById(providerId)).thenReturn(Optional.of(mockProvider));
        when(fruitRepository.save(any(Fruit.class))).thenReturn(savedEntity);

        FruitDTO result = fruitService.save(inputDto);

        assertNotNull(result.getId());
        assertEquals("Strawberry", result.getName());
        assertEquals(providerId, result.getProviderId());
        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    void save_ShouldThrowException_WhenProviderDoesNotExist() {
        FruitDTO inputDto = new FruitDTO(null, "Strawberry", 5, 999L);
        when(providerRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(FruitNotFoundException.class, () -> fruitService.save(inputDto));
        verify(fruitRepository, never()).save(any());
    }

    @Test
    void getOne_ShouldReturnDto_WhenExists() {
        Provider mockProvider = new Provider(1L, "Test Prov", "ES");
        Fruit entity = new Fruit(1L, "Pear", 10, mockProvider);
        when(fruitRepository.findById(1L)).thenReturn(Optional.of(entity));

        FruitDTO result = fruitService.getOne(1);

        assertEquals("Pear", result.getName());
        assertEquals(1L, result.getProviderId());
    }

    @Test
    void getOne_ShouldThrowException_WhenDoesNotExist() {
        when(fruitRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, () -> fruitService.getOne(99));
    }


    @Test
    void update_ShouldReturnDto_WhenFruitAndProviderExist() {
        Provider oldProvider = new Provider(1L, "Old Prov", "ES");
        Fruit existingFruit = new Fruit(1L, "Apple", 5, oldProvider);

        Long newProviderId = 2L;
        Provider newProvider = new Provider(newProviderId, "New Prov", "FR");
        FruitDTO updateDto = new FruitDTO(1L, "Green Apple", 6, newProviderId);

        Fruit updatedEntity = new Fruit(1L, "Green Apple", 6, newProvider);

        when(fruitRepository.findById(1L)).thenReturn(Optional.of(existingFruit));
        when(providerRepository.findById(newProviderId)).thenReturn(Optional.of(newProvider));
        when(fruitRepository.save(any(Fruit.class))).thenReturn(updatedEntity);

        FruitDTO result = fruitService.update(updateDto);

        assertEquals("Green Apple", result.getName());
        assertEquals(newProviderId, result.getProviderId());
    }

    @Test
    void update_ShouldThrowException_WhenProviderDoesNotExist() {
        Fruit existingFruit = new Fruit(1L, "Apple", 5, new Provider());
        FruitDTO updateDto = new FruitDTO(1L, "Apple", 5, 999L);

        when(fruitRepository.findById(1L)).thenReturn(Optional.of(existingFruit));
        when(providerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, () -> fruitService.update(updateDto));
    }

    @Test
    void delete_ShouldCallRepository_WhenExists() {
        when(fruitRepository.existsById(1L)).thenReturn(true);

        fruitService.delete(1);

        verify(fruitRepository).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowException_WhenDoesNotExist() {
        when(fruitRepository.existsById(99L)).thenReturn(false);

        assertThrows(FruitNotFoundException.class, () -> fruitService.delete(99));
    }

    @Test
    void getAll_ShouldReturnList() {
        Provider p = new Provider(1L, "P", "ES");
        List<Fruit> fruits = Arrays.asList(
                new Fruit(1L, "A", 1, p),
                new Fruit(2L, "B", 2, p)
        );
        when(fruitRepository.findAll()).thenReturn(fruits);

        List<FruitDTO> result = fruitService.getAll(null);

        assertEquals(2, result.size());
        assertEquals("A", result.getFirst().getName());
    }
}