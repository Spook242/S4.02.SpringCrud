package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.dto.ProviderDTO;
import cat.itacademy.s04.s02.n01.fruit.services.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<ProviderDTO> add(@Valid @RequestBody ProviderDTO providerDTO) {
        ProviderDTO savedProvider = providerService.save(providerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProvider);
    }

    @GetMapping
    public ResponseEntity<List<ProviderDTO>> getAll() {
        return ResponseEntity.ok(providerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(providerService.getOne(id));
    }

    @PutMapping
    public ResponseEntity<ProviderDTO> update(@Valid @RequestBody ProviderDTO providerDTO) {
        return ResponseEntity.ok(providerService.update(providerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        providerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}