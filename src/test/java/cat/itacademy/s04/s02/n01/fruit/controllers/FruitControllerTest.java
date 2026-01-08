package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
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
import static org.mockito.Mockito.doThrow;
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
        Fruit newFruit = new Fruit(null, "Apple", 5);
        Fruit savedFruit = new Fruit(1L, "Apple", 5);

        when(fruitService.save(any(Fruit.class))).thenReturn(savedFruit);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFruit)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getAllFruits_ShouldReturnList() throws Exception {
        List<Fruit> fruitList = List.of(new Fruit(1L, "Apple", 10));
        when(fruitService.getAll()).thenReturn(fruitList);

        mockMvc.perform(get("/fruits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getOneFruit_ShouldReturn200_WhenExists() throws Exception {
        Fruit existingFruit = new Fruit(1L, "Pear", 5);
        when(fruitService.getOne(1L)).thenReturn(existingFruit);

        mockMvc.perform(get("/fruits/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pear"));
    }

    @Test
    void updateFruit_ShouldReturn200_WhenExists() throws Exception {
        Fruit fruitToUpdate = new Fruit(1L, "Modified Apple", 12);
        when(fruitService.update(any(Fruit.class))).thenReturn(fruitToUpdate);

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