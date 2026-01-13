package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
        FruitDTO fruitDTO = new FruitDTO(null, "Banana", 10, 1L);
        FruitDTO savedFruit = new FruitDTO(1L, "Banana", 10, 1L);

        when(fruitService.save(any(FruitDTO.class))).thenReturn(savedFruit);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fruitDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Banana"))
                .andExpect(jsonPath("$.providerId").value(1));
    }

    @Test
    void updateFruit_ShouldReturn200_WhenExists() throws Exception {
        FruitDTO fruitToUpdate = new FruitDTO(1L, "Modified Banana", 15, 2L);

        when(fruitService.update(any(FruitDTO.class))).thenReturn(fruitToUpdate);

        mockMvc.perform(put("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fruitToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Modified Banana"));
    }

    @Test
    void getFruitById_ShouldReturn200_WhenExists() throws Exception {
        FruitDTO foundFruit = new FruitDTO(1L, "Pear", 5, 1L);

        when(fruitService.getOne(1)).thenReturn(foundFruit);

        mockMvc.perform(get("/fruits/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pear"));
    }

    @Test
    void getAllFruits_ShouldReturnList() throws Exception {
        List<FruitDTO> fruits = Arrays.asList(
                new FruitDTO(1L, "Apple", 10, 1L),
                new FruitDTO(2L, "Pear", 5, 1L)
        );

        when(fruitService.getAll()).thenReturn(fruits);

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deleteFruit_ShouldReturn204_WhenExists() throws Exception {
        doNothing().when(fruitService).delete(1);

        mockMvc.perform(delete("/fruits/1"))
                .andExpect(status().isNoContent());
    }
}