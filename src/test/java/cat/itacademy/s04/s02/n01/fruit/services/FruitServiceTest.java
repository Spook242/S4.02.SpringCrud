package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FruitServiceTest {

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitServiceImpl fruitService;

    @Test
    void save_ShouldReturnDto_WhenEntityIsSaved() {
        FruitDTO inputDto = new FruitDTO(null, "Strawberry", 5);
        Fruit savedEntity = new Fruit(1L, "Strawberry", 5);

        when(fruitRepository.save(any(Fruit.class))).thenReturn(savedEntity);

        FruitDTO result = fruitService.save(inputDto);

        assertNotNull(result.getId());
        assertEquals("Strawberry", result.getName());
        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    void getOne_ShouldReturnDto_WhenExists() {
        Fruit entity = new Fruit(1L, "Pear", 10);
        when(fruitRepository.findById(1L)).thenReturn(Optional.of(entity));

        FruitDTO result = fruitService.getOne(1L);

        assertEquals("Pear", result.getName());
    }

    @Test
    void getOne_ShouldThrowException_WhenDoesNotExist() {
        when(fruitRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, () -> fruitService.getOne(99L));
    }

    @Test
    void delete_ShouldCallRepository_WhenExists() {
        when(fruitRepository.existsById(1L)).thenReturn(true);

        fruitService.delete(1L);

        verify(fruitRepository).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowException_WhenDoesNotExist() {
        when(fruitRepository.existsById(99L)).thenReturn(false);

        assertThrows(FruitNotFoundException.class, () -> fruitService.delete(99L));
        verify(fruitRepository, never()).deleteById(99L);
    }
}