package com.bighibig.kombina.modules.order.services.findById;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.repository.OrderRepository;
import com.bighibig.kombina.modules.order.services.findById.FindOrderByIdService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindOrderByIdServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private FindOrderByIdService findOrderByIdService;

    @Test
    void shouldReturnOrderWhenExists() {
        Order order = Order.builder()
                .orderId(1L)
                .orderOwner("client1")
                .description("test")
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order result = findOrderByIdService.execute(1L);

        Assertions.assertNotNull(result, "Order not created");
        Assertions.assertEquals(order.getOrderId(), result.getOrderId(), "Order id not created");
        Assertions.assertEquals(order.getOrderOwner(), result.getOrderOwner(), "Order owner not created");
        Assertions.assertEquals(order.getDescription(), result.getDescription(), "Order description not created");

        verify(orderRepository, times(1)).findById(1L);
    }
}
