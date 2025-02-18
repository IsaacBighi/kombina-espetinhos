package com.bighibig.kombina.modules.order.controller;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.core.enums.OrderStatus;
import com.bighibig.kombina.modules.order.dto.create.CreateOrderDtoIn;
import com.bighibig.kombina.modules.order.dto.create.CreateOrderDtoOut;
import com.bighibig.kombina.modules.order.dto.update.UpdateOrderDtoIn;
import com.bighibig.kombina.modules.order.dto.update.UpdateOrderDtoOut;
import com.bighibig.kombina.modules.order.services.create.CreateOrderService;
import com.bighibig.kombina.modules.order.services.delete.DeleteOrderService;
import com.bighibig.kombina.modules.order.services.findAll.FindAllOrdersService;
import com.bighibig.kombina.modules.order.services.findById.FindOrderByIdService;
import com.bighibig.kombina.modules.order.services.findByStatus.FindOrdersByStatusService;
import com.bighibig.kombina.modules.order.services.update.UpdateOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FindAllOrdersService findAllOrdersService;

    @MockitoBean
    private FindOrderByIdService findOrderByIdService;

    @MockitoBean
    private FindOrdersByStatusService findOrdersByStatusService;

    @MockitoBean
    private CreateOrderService createOrderService;

    @MockitoBean
    private UpdateOrderService updateOrderService;

    @MockitoBean
    private DeleteOrderService deleteOrderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFindOrders() throws Exception {
        Order order = Order.builder()
                .orderId(1L)
                .orderOwner("client1")
                .description("test")
                .build();

        List<Order> orders = List.of(order);

        when(findAllOrdersService.execute()).thenReturn(orders);

        mockMvc.perform(get("/order")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].orderOwner").value("client1"))
                .andExpect(jsonPath("$[0].description").value("test"));
    }

    @Test
    void testFindOrderById() throws Exception {
        Order order = Order.builder()
                .orderId(1L)
                .orderOwner("client1")
                .description("test")
                .build();

        when(findOrderByIdService.execute(order.getOrderId())).thenReturn(order);

        mockMvc.perform(get("/order/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.orderOwner").value("client1"))
                .andExpect(jsonPath("$.description").value("test"));
    }

    @Test
    void testFindOrdersByStatus() throws Exception {
        Order order = Order.builder()
                .orderId(1L)
                .orderOwner("client1")
                .description("test")
                .orderStatus(OrderStatus.PENDING)
                .build();

        List<Order> orders = List.of(order);

        when(findOrdersByStatusService.execute()).thenReturn(orders);

        mockMvc.perform(get("/order/pending")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].orderOwner").value("client1"))
                .andExpect(jsonPath("$[0].description").value("test"))
                .andExpect(jsonPath("$[0].orderStatus").value("PENDING"));
    }

    @Test
    void testCreateOrder() throws Exception {
        CreateOrderDtoIn request = new CreateOrderDtoIn("client1", "test");
        CreateOrderDtoOut response = new CreateOrderDtoOut(1L, "client1", "test", OrderStatus.PENDING, BigDecimal.ZERO);

        when(createOrderService.execute(any(CreateOrderDtoIn.class))).thenReturn(response);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.orderOwner").value("client1"))
                .andExpect(jsonPath("$.description").value("test"));
    }

    @Test
    void testUpdateOrder() throws Exception {
        Order order = Order.builder()
                .orderId(1L)
                .orderOwner("old client")
                .description("test")
                .orderStatus(OrderStatus.PENDING)
                .build();

        UpdateOrderDtoIn request = new UpdateOrderDtoIn("new client", "test", OrderStatus.PAID, BigDecimal.ZERO);
        UpdateOrderDtoOut response = new UpdateOrderDtoOut(1L, "new client", "test", OrderStatus.PAID, BigDecimal.ZERO);

        when(updateOrderService.execute(any(Long.class), any(UpdateOrderDtoIn.class))).thenReturn(response);

        mockMvc.perform(put("/order/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.orderOwner").value("new client"))
                .andExpect(jsonPath("$.description").value("test"))
                .andExpect(jsonPath("$.orderStatus").value("PAID"));
    }

    @Test
    void testDeleteOrder() throws Exception {
        doNothing().when(deleteOrderService).execute(any(Long.class));

        mockMvc.perform(delete("/order/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
