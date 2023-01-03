package com.assignment.storemanagementtool.service;

import com.assignment.storemanagementtool.dto.OrderDTO;
import com.assignment.storemanagementtool.dto.ProductDTO;
import com.assignment.storemanagementtool.dto.UserDTO;
import com.assignment.storemanagementtool.entity.Order;
import com.assignment.storemanagementtool.entity.ProductStock;
import com.assignment.storemanagementtool.exception.OrderNotFoundException;
import com.assignment.storemanagementtool.exception.OutOfStockException;
import com.assignment.storemanagementtool.exception.ProductNotFoundException;
import com.assignment.storemanagementtool.repository.OrderRepository;
import com.assignment.storemanagementtool.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

  private OrderRepository orderRepository;
  private ProductStockRepository productStockRepository;

  public Order createOrder(OrderDTO orderDTO) {
  return null;
  }

  public Order updateOrder(OrderDTO orderDTO) {

    return null;
  }

  public Order findOrderById(Long id) {
    return null;
  }

  public List<Order> findUserOrders(UserDTO userDTO) {
    return null;
  }

  public void deleteOrderById(Long id) {

  }

  public void deleteUserOrders(UserDTO userDTO) {

  }
}
