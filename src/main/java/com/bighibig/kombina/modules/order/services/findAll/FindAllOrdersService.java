package com.bighibig.kombina.modules.order.services.findAll;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllOrdersService {
    private final OrderRepository orderRepository;

    public FindAllOrdersService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> execute() {
        return orderRepository.findAll();
    }
}
