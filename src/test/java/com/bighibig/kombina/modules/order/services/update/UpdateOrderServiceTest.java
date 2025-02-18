package com.bighibig.kombina.modules.order.services.update;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.core.enums.OrderStatus;
import com.bighibig.kombina.modules.order.dto.update.UpdateOrderDtoIn;
import com.bighibig.kombina.modules.order.dto.update.UpdateOrderDtoOut;
import com.bighibig.kombina.modules.order.exceptions.OrderNotFoundException;
import com.bighibig.kombina.modules.order.repository.OrderRepository;
import com.bighibig.kombina.modules.order.services.update.UpdateOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateOrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private UpdateOrderService updateOrderService;

    private long orderId;
    private Order existingOrder;
    private UpdateOrderDtoIn updateDto;

    @BeforeEach
    void setUp() {
        orderId = 1L;

        existingOrder = Order.builder()
                .orderId(orderId)
                .orderOwner("old client")
                .description("old description")
                .totalPrice(BigDecimal.valueOf(100))
                .orderStatus(OrderStatus.PENDING)
                .build();

        updateDto = new UpdateOrderDtoIn(
                "new client",
                "new description",
                OrderStatus.PAID,
                BigDecimal.valueOf(200)
        );
    }

    @Test
    void shouldUpdateOrderSuccessfully() {
        Order updatedOrder = Order.builder()
                .orderId(orderId)
                .orderOwner(updateDto.orderOwner())
                .description(updateDto.description())
                .totalPrice(updateDto.totalPrice())
                .orderStatus(updateDto.status())
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        UpdateOrderDtoOut result = updateOrderService.execute(orderId, updateDto);

        Assertions.assertNotNull(result, "order should not be null");
        Assertions.assertEquals(orderId, result.orderId(), "order id should be the same");
        Assertions.assertEquals(updateDto.orderOwner(), result.orderOwner(), "order owner should be the same");
        Assertions.assertEquals(updateDto.description(), result.description(), "description should be the same");
        Assertions.assertEquals(updateDto.totalPrice(), result.totalPrice(), "total price should be the same");
        Assertions.assertEquals(updateDto.status(), result.orderStatus(), "status should be the same");

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldThrowOrderNotFoundException() {
        long nonExistingOrder = 2L;

        when(orderRepository.findById(nonExistingOrder)).thenReturn(Optional.empty());

        Assertions.assertThrows(OrderNotFoundException.class, () -> {
            updateOrderService.execute(nonExistingOrder, updateDto);
        });

        verify(orderRepository, times(1)).findById(nonExistingOrder);
        verify(orderRepository, never()).save(any(Order.class));
    }
}
