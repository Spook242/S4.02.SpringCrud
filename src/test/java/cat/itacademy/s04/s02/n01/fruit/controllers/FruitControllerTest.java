package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        mockMvc.perform(post("/fruits/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFruit)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(5));
    }

    @Test
    void addFruit_ShouldReturn400_WhenDataIsInvalid() throws Exception {
        Fruit invalidFruit = new Fruit(null, "", -5);

        mockMvc.perform(post("/fruits/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFruit)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllFruits_ShouldReturnList() throws Exception {
        List<Fruit> fruitList = List.of(
                new Fruit(1L, "Apple", 10),
                new Fruit(2L, "Pear", 5)
        );

        when(fruitService.getAll()).thenReturn(fruitList);

        mockMvc.perform(get("/fruits/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Apple"));
    }

    @Test
    void getOneFruit_ShouldReturn200_WhenExists() throws Exception {
        Fruit existingFruit = new Fruit(1L, "Pear", 5);

        when(fruitService.getOne(1L)).thenReturn(existingFruit);

        mockMvc.perform(get("/fruits/getOne/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pear"));
    }

    @Test
    void getOneFruit_ShouldReturn404_WhenDoesNotExist() throws Exception {
        when(fruitService.getOne(99L)).thenThrow(new FruitNotFoundException("Fruit not found"));

        mockMvc.perform(get("/fruits/getOne/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}