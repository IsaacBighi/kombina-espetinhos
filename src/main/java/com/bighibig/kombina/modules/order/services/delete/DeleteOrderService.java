package com.bighibig.kombina.modules.order.services.delete;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.exceptions.OrderNotFoundException;
import com.bighibig.kombina.modules.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteOrderService {
    private final OrderRepository orderRepository;

    public DeleteOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void execute(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));

        orderRepository.deleteById(order.getOrderId());
    }
}
