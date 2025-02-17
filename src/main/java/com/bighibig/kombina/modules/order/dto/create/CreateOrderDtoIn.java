package com.bighibig.kombina.modules.order.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderDtoIn(
        @NotBlank(message = "Order owner is required")
        @Size(max = 100)
        String orderOwner,
        String description
) {}
