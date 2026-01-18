package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.domain.Order;
import cat.itacademy.s04.s02.n01.fruit.domain.OrderLine;
import cat.itacademy.s04.s02.n01.fruit.repository.mongo.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        if (order.getClientName() == null || order.getClientName().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required.");
        }
        if (order.getOrderLines() == null || order.getOrderLines().isEmpty()) {
            throw new IllegalArgumentException("The order must have at least one fruit.");
        }

        for (OrderLine line : order.getOrderLines()) {
            if (line.getQuantity() <= 0) {
                throw new IllegalArgumentException("The amount for " + line.getFruitName() + " must be positive.");
            }
        }

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        if (order.getDeliveryDate() == null || order.getDeliveryDate().isBefore(tomorrow)) {
            throw new IllegalArgumentException("The delivery date must be from tomorrow.");
        }

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order updateOrder(String id, Order newOrderData) {
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setClientName(newOrderData.getClientName());
            existingOrder.setDeliveryDate(newOrderData.getDeliveryDate());
            existingOrder.setOrderLines(newOrderData.getOrderLines());

            return orderRepository.save(existingOrder);
        }).orElse(null);
    }
}