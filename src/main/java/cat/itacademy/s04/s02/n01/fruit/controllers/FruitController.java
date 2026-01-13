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
    public ResponseEntity<FruitDTO> add(@Valid @RequestBody FruitDTO fruitDTO) {
        FruitDTO newFruit = fruitService.save(fruitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFruit);
    }


    @PutMapping
    public ResponseEntity<FruitDTO> update(@Valid @RequestBody FruitDTO fruitDTO) {
        FruitDTO updatedFruit = fruitService.update(fruitDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedFruit);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        fruitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FruitDTO> getOne(@PathVariable int id) {
        FruitDTO fruit = fruitService.getOne(id);
        return ResponseEntity.ok(fruit);
    }

    @GetMapping
    public ResponseEntity<List<FruitDTO>> getAll() {
        return ResponseEntity.ok(fruitService.getAll());
    }
}