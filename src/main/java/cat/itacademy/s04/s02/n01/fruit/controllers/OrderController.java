package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.domain.Order;
import cat.itacademy.s04.s02.n01.fruit.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // --- TASK 1: Crear Comanda (POST) ---
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            Order savedOrder = orderService.createOrder(order);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            // Captura errores de validación (fechas pasadas, sin frutas, etc.)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }

    // --- TASK 2: Listar Comandes (GET) ---
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        // Si la lista está vacía, devuelve [] con 200 OK
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Opcional: 204 No Content si prefieres
        }

        return new ResponseEntity<>(orders, HttpStatus.OK); // 200 OK
    }
}