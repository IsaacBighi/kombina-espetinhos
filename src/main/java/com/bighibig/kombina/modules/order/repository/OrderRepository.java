package com.bighibig.kombina.modules.order.repository;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.core.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderStatus(OrderStatus status);

    default List<Order> findPendingOrders() {
        return findByOrderStatus(OrderStatus.PENDING);
    }
}
