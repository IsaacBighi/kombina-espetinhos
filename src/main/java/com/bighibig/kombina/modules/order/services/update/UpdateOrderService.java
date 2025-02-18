package com.bighibig.kombina.modules.order.services.update;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.dto.update.UpdateOrderDtoIn;
import com.bighibig.kombina.modules.order.dto.update.UpdateOrderDtoOut;
import com.bighibig.kombina.modules.order.exceptions.OrderNotFoundException;
import com.bighibig.kombina.modules.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderService {
    private final OrderRepository orderRepository;

    public UpdateOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public UpdateOrderDtoOut execute(Long orderId, UpdateOrderDtoIn dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID" + orderId + " not found" ));

        if(dto.orderOwner() != null) {
            order.setOrderOwner(dto.orderOwner());
        }
        if(dto.description() != null) {
            order.setDescription(dto.description());
        }
        if(dto.totalPrice() != null) {
            order.setTotalPrice(dto.totalPrice());
        }
        if(dto.status() != null) {
            order.setOrderStatus(dto.status());
        }

        orderRepository.save(order);

        return new UpdateOrderDtoOut(
                order.getOrderId(),
                order.getOrderOwner(),
                order.getDescription(),
                order.getOrderStatus(),
                order.getTotalPrice()
        );
    }
}
