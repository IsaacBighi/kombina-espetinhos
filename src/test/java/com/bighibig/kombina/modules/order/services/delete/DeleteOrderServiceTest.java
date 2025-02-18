package com.bighibig.kombina.modules.order.services.delete;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.exceptions.OrderNotFoundException;
import com.bighibig.kombina.modules.order.repository.OrderRepository;
import com.bighibig.kombina.modules.order.services.delete.DeleteOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteOrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private DeleteOrderService deleteOrderService;

    @Test
    void shouldDeleteOrderSuccessfully() {
        long orderId = 1L;
        Order order = Order.builder()
                .orderId(orderId)
                .orderOwner("client1")
                .description("test")
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        deleteOrderService.execute(orderId);

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void shouldThrowOrderNotFoundException() {
        long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Assertions.assertThrows(OrderNotFoundException.class, () -> {
            deleteOrderService.execute(orderId);
        });

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, never()).deleteById(anyLong());
    }
}
