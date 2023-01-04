package com.assignment.storemanagementtool.controller;

import com.assignment.storemanagementtool.dto.OrderDTO;
import com.assignment.storemanagementtool.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Secured("ROLE_ADMIN")
public class AdminOrderController {

  private OrderService orderService;

  @PutMapping("/")
  public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO) {
    return orderService.updateOrder(orderDTO);
  }
}
