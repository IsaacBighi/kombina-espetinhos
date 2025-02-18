package com.bighibig.kombina.modules.order.services.create;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.core.enums.OrderStatus;
import com.bighibig.kombina.modules.order.dto.create.CreateOrderDtoIn;
import com.bighibig.kombina.modules.order.dto.create.CreateOrderDtoOut;
import com.bighibig.kombina.modules.order.exceptions.InvalidOrderException;
import com.bighibig.kombina.modules.order.repository.OrderRepository;
import com.bighibig.kombina.modules.order.services.create.CreateOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateOrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CreateOrderService createOrderService;

    @Test
    void shouldCreateOrderSuccessfully() {
        CreateOrderDtoIn dto = new CreateOrderDtoIn("client1", "test");
        Order order = Order.builder()
                .orderId(1L)
                .orderOwner("client1")
                .description("test")
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        CreateOrderDtoOut result = createOrderService.execute(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(order.getOrderId(), result.orderId(), "Order id not created");
        Assertions.assertEquals(order.getOrderOwner(), result.orderOwner(), "Order owner not created");
        Assertions.assertEquals(order.getDescription(), result.description(), "Order description not created");
        Assertions.assertEquals(OrderStatus.PENDING, result.status(), "Order status not created");
    }

    @Test
    void shouldThrowInvalidOrderException() {
        CreateOrderDtoIn dto = new CreateOrderDtoIn("", "test");

        Assertions.assertThrows(InvalidOrderException.class, () -> createOrderService.execute(dto));
    }
}
