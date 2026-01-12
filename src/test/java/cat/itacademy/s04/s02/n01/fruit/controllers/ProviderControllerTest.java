package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.dto.ProviderDTO;
import cat.itacademy.s04.s02.n01.fruit.services.ProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProviderController.class)
class ProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProviderService providerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addProvider_ShouldReturn201_WhenDataIsValid() throws Exception {
        ProviderDTO providerDTO = new ProviderDTO(null, "Pujol´s Fruit", "Catalonia");
        ProviderDTO savedDTO = new ProviderDTO(1L, "Pujol´s Fruit", "Catalonia");

        when(providerService.save(any(ProviderDTO.class))).thenReturn(savedDTO);

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(providerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pujol´s Fruit"));
    }
}
