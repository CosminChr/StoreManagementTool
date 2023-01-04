package com.assignment.storemanagementtool.controller;

import com.assignment.storemanagementtool.dto.OrderDTO;
import com.assignment.storemanagementtool.service.OrderService;
import lombok.AllArgsConstructor;
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
public class BuyerOrderController {

  private OrderService orderService;

  @PostMapping("/")
  public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO, Authentication auth) {
    return ResponseEntity.status(201).body(orderService.createOrder(orderDTO, auth));
  }

  @GetMapping("/")
  public ResponseEntity<List<OrderDTO>> findUserOrders(Authentication auth) {
    return ResponseEntity.ok(orderService.findUserOrders(auth));
  }
}
