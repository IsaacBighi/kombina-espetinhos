package com.bighibig.kombina.modules.order.services.create;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.dto.create.CreateOrderDtoIn;
import com.bighibig.kombina.modules.order.dto.create.CreateOrderDtoOut;
import com.bighibig.kombina.modules.order.exceptions.InvalidOrderException;
import com.bighibig.kombina.modules.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderService {
    private final OrderRepository orderRepository;

    public CreateOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreateOrderDtoOut execute(CreateOrderDtoIn dto) {
        if (dto.orderOwner() == null || dto.orderOwner().isBlank()) {
            throw new InvalidOrderException("Order owner is required");
        }

        Order order = Order.builder()
                .orderOwner(dto.orderOwner())
                .description(dto.description())
                .build();

        Order savedOrder = orderRepository.save(order);

        return new CreateOrderDtoOut(
                savedOrder.getOrderId(),
                savedOrder.getOrderOwner(),
                savedOrder.getDescription(),
                savedOrder.getOrderStatus(),
                savedOrder.getTotalPrice()
        );
    }
}

