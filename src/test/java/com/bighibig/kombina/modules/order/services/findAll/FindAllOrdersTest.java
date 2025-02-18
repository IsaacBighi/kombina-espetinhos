package com.bighibig.kombina.modules.order.services.findAll;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.core.enums.OrderStatus;
import com.bighibig.kombina.modules.order.repository.OrderRepository;
import com.bighibig.kombina.modules.order.services.findAll.FindAllOrdersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindAllOrdersTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private FindAllOrdersService findAllOrdersService;

    @Test
    void shouldReturnAllOrders() {
        Order order1 = Order.builder()
                .orderId(1L)
                .orderOwner("client1")
                .description("test")
                .orderStatus(OrderStatus.PENDING)
                .build();

        Order order2 = Order.builder()
                .orderId(2L)
                .orderOwner("client2")
                .description("test")
                .orderStatus(OrderStatus.PENDING)
                .build();

        List<Order> mockOrders = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(mockOrders);
        List<Order> result = findAllOrdersService.execute();


        Assertions.assertFalse(result.isEmpty(), "Orders should not be empty");
        Assertions.assertEquals(2, result.size(), "Orders should have 2 elements");
        Assertions.assertEquals(order1.getOrderOwner(), result.getFirst().getOrderOwner(), "Order owner should match");
        Assertions.assertEquals(order2.getOrderOwner(), result.getLast().getOrderOwner(), "Order owner should match");

        verify(orderRepository, times(1)).findAll();
    }
}
