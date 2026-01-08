package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import cat.itacademy.s04.s02.n01.fruit.exception.FruitNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FruitController.class)
class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FruitService fruitService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addFruit_ShouldReturn201_WhenDataIsValid() throws Exception {
        FruitDTO newFruitDTO = new FruitDTO(null, "Apple", 5);
        FruitDTO savedFruitDTO = new FruitDTO(1L, "Apple", 5);

        when(fruitService.save(any(FruitDTO.class))).thenReturn(savedFruitDTO);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFruitDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Apple"));
    }

    @Test
    void addFruit_ShouldReturn400_WhenDataIsInvalid() throws Exception {
        FruitDTO invalidFruitDTO = new FruitDTO(null, "", -5);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFruitDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.weightInKilos").exists());
    }

    @Test
    void getAllFruits_ShouldReturnList() throws Exception {
        List<FruitDTO> fruitList = List.of(
                new FruitDTO(1L, "Apple", 10),
                new FruitDTO(2L, "Pear", 5)
        );

        when(fruitService.getAll()).thenReturn(fruitList);

        mockMvc.perform(get("/fruits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Apple"));
    }

    @Test
    void getOneFruit_ShouldReturn200_WhenExists() throws Exception {
        FruitDTO existingFruit = new FruitDTO(1L, "Pear", 5);
        when(fruitService.getOne(1L)).thenReturn(existingFruit);

        mockMvc.perform(get("/fruits/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pear"));
    }

    @Test
    void getOneFruit_ShouldReturn404_WhenDoesNotExist() throws Exception {
        when(fruitService.getOne(99L)).thenThrow(new FruitNotFoundException("Fruit not found"));

        mockMvc.perform(get("/fruits/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateFruit_ShouldReturn200_WhenExists() throws Exception {
        FruitDTO fruitToUpdate = new FruitDTO(1L, "Modified Apple", 12);

        when(fruitService.update(any(FruitDTO.class))).thenReturn(fruitToUpdate);

        mockMvc.perform(put("/fruits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fruitToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Modified Apple"));
    }

    @Test
    void deleteFruit_ShouldReturn204_WhenExists() throws Exception {
        mockMvc.perform(delete("/fruits/1"))
                .andExpect(status().isNoContent());
    }
}