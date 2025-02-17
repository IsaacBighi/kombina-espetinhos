package com.bighibig.kombina.modules.order.services.findById;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.exceptions.OrderNotFoundException;
import com.bighibig.kombina.modules.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindOrderByIdService {
    private final OrderRepository orderRepository;

    public FindOrderByIdService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order with " + orderId + " not found")
        );
    }
}
