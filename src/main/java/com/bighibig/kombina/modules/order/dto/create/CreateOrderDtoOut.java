package com.bighibig.kombina.modules.order.dto.create;

import com.bighibig.kombina.modules.order.core.enums.OrderStatus;

import java.math.BigDecimal;

public record CreateOrderDtoOut(
        Long orderId,
        String orderOwner,
        String description,
        OrderStatus status,
        BigDecimal totalPrice
) {}
