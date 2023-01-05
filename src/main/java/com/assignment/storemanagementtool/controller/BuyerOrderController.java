package com.assignment.storemanagementtool.controller;

import com.assignment.storemanagementtool.dto.OrderDTO;
import com.assignment.storemanagementtool.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Secured("ROLE_BUYER")
@Slf4j
public class BuyerOrderController {

  private OrderService orderService;

  @PostMapping("/")
  public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO, Authentication auth) {
    OrderDTO order = orderService.createOrder(orderDTO, auth);
    log.info("The order with id {} was successfully created for user {}", order.getId(), auth.getPrincipal());
    return ResponseEntity.status(201).body(order);
  }

  @GetMapping("/")
  public ResponseEntity<List<OrderDTO>> findUserOrders(Authentication auth) {
    List<OrderDTO> buyerOrders = orderService.findBuyerOrders(auth);
    log.info("The orders {} were successfully retrieved", buyerOrders);
    return ResponseEntity.ok(buyerOrders);
  }
}
