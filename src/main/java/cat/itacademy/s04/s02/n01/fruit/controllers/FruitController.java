package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping
    public ResponseEntity<FruitDTO> addFruit(@Valid @RequestBody FruitDTO fruitDTO) {
        FruitDTO newFruit = fruitService.save(fruitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFruit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FruitDTO> updateFruit(@PathVariable Long id, @Valid @RequestBody FruitDTO fruitDTO) {
        fruitDTO.setId(id);
        FruitDTO updatedFruit = fruitService.update(fruitDTO);
        return ResponseEntity.ok(updatedFruit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
        fruitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FruitDTO> getFruitById(@PathVariable Long id) {
        return ResponseEntity.ok(fruitService.getOne(id));
    }

    @GetMapping
    public ResponseEntity<List<FruitDTO>> getAllFruits() {
        return ResponseEntity.ok(fruitService.getAll());
    }
}