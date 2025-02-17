package com.bighibig.kombina.modules.order.controller;

import com.bighibig.kombina.modules.order.core.Order;
import com.bighibig.kombina.modules.order.dto.create.CreateOrderDtoIn;
import com.bighibig.kombina.modules.order.dto.create.CreateOrderDtoOut;
import com.bighibig.kombina.modules.order.dto.update.UpdateOrderDtoIn;
import com.bighibig.kombina.modules.order.dto.update.UpdateOrderDtoOut;
import com.bighibig.kombina.modules.order.services.create.CreateOrderService;
import com.bighibig.kombina.modules.order.services.delete.DeleteOrderService;
import com.bighibig.kombina.modules.order.services.findAll.FindAllOrdersService;
import com.bighibig.kombina.modules.order.services.findById.FindOrderByIdService;
import com.bighibig.kombina.modules.order.services.update.UpdateOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final FindOrderByIdService findOrderByIdService;
    private final FindAllOrdersService findAllOrdersService;
    private final CreateOrderService createOrderService;
    private final UpdateOrderService updateOrderService;
    private final DeleteOrderService deleteOrderService;

    public OrderController(
            FindOrderByIdService findOrderByIdService,
            FindAllOrdersService findAllOrdersService,
            CreateOrderService createOrderService,
            UpdateOrderService updateOrderService,
            DeleteOrderService deleteOrderService
    ) {
        this.findOrderByIdService = findOrderByIdService;
        this.findAllOrdersService = findAllOrdersService;
        this.createOrderService = createOrderService;
        this.updateOrderService = updateOrderService;
        this.deleteOrderService = deleteOrderService;
    }

    @GetMapping
    public List<Order> findAll() {
        return findAllOrdersService.execute();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable long id) {
        Order foundOrder = findOrderByIdService.execute(id);
        return new ResponseEntity<Order>(foundOrder, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateOrderDtoOut> createOrder(@Valid @RequestBody CreateOrderDtoIn dto) {
        CreateOrderDtoOut createdOrder = createOrderService.execute(dto);
        return new ResponseEntity<CreateOrderDtoOut>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateOrderDtoOut> updateOrder(@PathVariable long id, @RequestBody UpdateOrderDtoIn dto) {
        UpdateOrderDtoOut updateOrder = updateOrderService.execute(id, dto);
        return new ResponseEntity<UpdateOrderDtoOut>(updateOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable long id) {
        deleteOrderService.execute(id);
        return ResponseEntity.ok().body("Order deleted");
    }
}
