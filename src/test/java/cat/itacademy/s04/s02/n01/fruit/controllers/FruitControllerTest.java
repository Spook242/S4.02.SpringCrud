package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        // GIVEN: Una fruta válida
        Fruit newFruit = new Fruit(null, "Manzana", 5);
        Fruit savedFruit = new Fruit(1L, "Manzana", 5);

        when(fruitService.save(any(Fruit.class))).thenReturn(savedFruit);

        mockMvc.perform(post("/fruits/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFruit)))
                .andExpect(status().isCreated()) // Esperamos un 201 Created
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Manzana"))
                .andExpect(jsonPath("$.weightInKilos").value(5));
    }

    @Test
    void addFruit_ShouldReturn400_WhenDataIsInvalid() throws Exception {
        // GIVEN: Una fruita amb dades incorrectes (nom buit i pes negatiu)
        Fruit invalidFruit = new Fruit(null, "", -5);

        // WHEN & THEN: Esperem un 400 Bad Request
        mockMvc.perform(post("/fruits/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFruit)))
                .andExpect(status().isBadRequest()); // <--- Això fallarà ara mateix
    }
}
