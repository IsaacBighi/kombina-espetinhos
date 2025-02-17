package com.bighibig.kombina.modules.order.dto.update;

import com.bighibig.kombina.modules.order.core.enums.OrderStatus;

import java.math.BigDecimal;

public record UpdateOrderDtoIn(
        String orderOwner,
        String description,
        OrderStatus status,
        BigDecimal totalPrice
) {}
