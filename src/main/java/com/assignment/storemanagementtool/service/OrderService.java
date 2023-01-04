package com.assignment.storemanagementtool.service;

import com.assignment.storemanagementtool.dto.OrderDTO;
import com.assignment.storemanagementtool.dto.ProductDTO;
import com.assignment.storemanagementtool.dto.BuyerDTO;
import com.assignment.storemanagementtool.entity.Order;
import com.assignment.storemanagementtool.entity.ProductStock;
import com.assignment.storemanagementtool.exception.OrderNotFoundException;
import com.assignment.storemanagementtool.exception.OutOfStockException;
import com.assignment.storemanagementtool.exception.ProductNotFoundException;
import com.assignment.storemanagementtool.mapper.OrderMapper;
import com.assignment.storemanagementtool.mapper.BuyerMapper;
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
    for (ProductDTO product : orderDTO.getProducts()) {
      ProductStock productStock = productStockRepository.findByName(product.getName())
          .orElseThrow(() -> new ProductNotFoundException(String.format("The product with name %s was not found", product.getName())));
      if (productStock.getQuantity() < product.getQuantity()) {
        throw new OutOfStockException(String.format("The order could not be created because the product %s is out of stock", product.getName()));
      }
    }
    return orderRepository.save(OrderMapper.mapDtoToEntity(orderDTO));
  }

  public Order updateOrder(OrderDTO orderDTO) {
    Order orderToUpdate = orderRepository.findById(orderDTO.getId())
        .orElseThrow(() -> new OrderNotFoundException(String.format("The order with id %s does not exist", orderDTO.getId())));
    for (ProductDTO product : orderDTO.getProducts()) {
      ProductStock productStock = productStockRepository.findByName(product.getName())
          .orElseThrow(() -> new ProductNotFoundException(String.format("The product with name %s was not found", product)));
      if (productStock.getQuantity() < product.getQuantity()) {
        throw new OutOfStockException(String.format("The order could not be updated because the product %s is out of stock", product.getName()));
      }
    }

    return orderRepository.save(orderToUpdate);
  }

  public Order findOrderById(Long id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(String.format("The order with id %s does not exist", id)));
  }

  public List<Order> findUserOrders(BuyerDTO buyerDTO) {
    return orderRepository.findByBuyer(BuyerMapper.mapDtoToEntity(buyerDTO));
  }

  public void deleteOrderById(Long id) {
    orderRepository.deleteById(id);
  }

  public void deleteUserOrders(BuyerDTO buyerDTO) {
    orderRepository.deleteByBuyer(BuyerMapper.mapDtoToEntity(buyerDTO));
  }
}
