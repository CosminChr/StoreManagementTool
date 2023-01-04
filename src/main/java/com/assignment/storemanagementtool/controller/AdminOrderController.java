package com.assignment.storemanagementtool.controller;

import com.assignment.storemanagementtool.dto.OrderDTO;
import com.assignment.storemanagementtool.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Secured("ROLE_ADMIN")
public class AdminOrderController {

  private OrderService orderService;

  @PutMapping("/")
  public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO) {
    return ResponseEntity.ok(orderService.updateOrder(orderDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteOrderById(@PathVariable("id") Long id) {
    orderService.deleteOrderById(id);
    return ResponseEntity.ok("The order was successfully deleted");
  }

  @DeleteMapping("/")
  public ResponseEntity<String> deleteUserOrders(@QueryParam("usermame") String username) {
    orderService.deleteUserOrders(username);
    return ResponseEntity.ok("The orders were successfully deleted");
  }
}
