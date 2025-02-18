package com.bighibig.kombina.modules.order.repository;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.core.enums.OrderStatus;
import com.bighibig.kombina.modules.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    @Test
    void shouldReturnAllOrders() {
        Order order1 = Order.builder()
                .orderOwner("client1")
                .description("test")
                .build();

        Order order2 = Order.builder()
                .orderOwner("client2")
                .description("test")
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);

        List<Order> orders = orderRepository.findAll();

        Assertions.assertEquals(2, orders.size(), "Should be two orders");
    }

    @Test
    void shouldReturnOrder() {
        Order order = Order.builder()
                .orderOwner("client1")
                .description("test")
                .build();

        orderRepository.save(order);
        Optional<Order> foundOrder = orderRepository.findById(order.getOrderId());

        Assertions.assertTrue(foundOrder.isPresent(), "Order should exist");
    }

    @Test
    void shouldReturnOrderStatusPending() {
        Order order1 = Order.builder()
                .orderOwner("client1")
                .description("test")
                .build();

        Order order2 = Order.builder()
                .orderOwner("client2")
                .description("test")
                .build();

        Order order3 = Order.builder()
                .orderOwner("client3")
                .description("test")
                .orderStatus(OrderStatus.PAID)
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);

        List<Order> pendingOrders = orderRepository.findPendingOrders();

        Assertions.assertEquals(2, pendingOrders.size());
    }

    @Test
    void shouldSaveAndFindOrder() {
        Order order = Order.builder()
                .orderOwner("client1")
                .description("test")
                .build();

        Order savedOrder = orderRepository.save(order);
        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getOrderId());

        Assertions.assertTrue(foundOrder.isPresent());
        Assertions.assertEquals(savedOrder.getOrderOwner(), foundOrder.get().getOrderOwner(), "Order owner should match");
        Assertions.assertEquals(savedOrder.getDescription(), foundOrder.get().getDescription(), "Order description should match");
    }

    @Test
    void shouldUpdateOrder() {
        Order order = Order.builder()
                .orderOwner("client1")
                .description("test1")
                .orderStatus(OrderStatus.PAID)
                .build();

        orderRepository.save(order);

        order.setOrderOwner("client2");
        order.setDescription("test2");
        order.setOrderStatus(OrderStatus.PAID);

        Order updatedOrder = orderRepository.save(order);
        Optional<Order> foundOrder = orderRepository.findById(updatedOrder.getOrderId());

        Assertions.assertTrue(foundOrder.isPresent());
        Assertions.assertEquals("client2", foundOrder.get().getOrderOwner(), "Order owner should match");
        Assertions.assertEquals("test2", foundOrder.get().getDescription(), "Order description should match");
        Assertions.assertEquals(OrderStatus.PAID, updatedOrder.getOrderStatus(), "Order status should match");
    }

    @Test
    void shouldDeleteOrderById() {
        Order order = Order.builder()
                .orderOwner("client1")
                .description("test")
                .build();

        Order savedOrder = orderRepository.save(order);
        long orderId = savedOrder.getOrderId();

        Assertions.assertTrue(orderRepository.findById(orderId).isPresent());
        orderRepository.deleteById(orderId);
        Optional<Order> deletedOrder = orderRepository.findById(orderId);
        Assertions.assertFalse(deletedOrder.isPresent(), "Order should not exist");
    }
}
