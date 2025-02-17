package com.bighibig.kombina.modules.order.services.findByStatus;

import com.bighibig.kombina.modules.order.core.Order;

import com.bighibig.kombina.modules.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindOrdersByStatusService {
    private final OrderRepository orderRepository;

    public FindOrdersByStatusService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> execute() {
        return orderRepository.findPendingOrders();
    }
}
