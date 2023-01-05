package com.assignment.storemanagementtool.service;

import com.assignment.storemanagementtool.dto.OrderDTO;
import com.assignment.storemanagementtool.dto.ProductDTO;
import com.assignment.storemanagementtool.entity.Buyer;
import com.assignment.storemanagementtool.entity.Order;
import com.assignment.storemanagementtool.entity.ProductStock;
import com.assignment.storemanagementtool.exception.OrderNotFoundException;
import com.assignment.storemanagementtool.exception.OutOfStockException;
import com.assignment.storemanagementtool.exception.ProductNotFoundException;
import com.assignment.storemanagementtool.mapper.OrderMapper;
import com.assignment.storemanagementtool.mapper.ProductMapper;
import com.assignment.storemanagementtool.repository.OrderRepository;
import com.assignment.storemanagementtool.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

  private OrderRepository orderRepository;
  private ProductStockRepository productStockRepository;
  private BuyerService buyerService;

  public OrderDTO createOrder(OrderDTO orderDTO, Authentication auth) {
    var buyer = buyerService.findBuyerByUsername(String.valueOf(auth.getPrincipal()));
    for (ProductDTO product : orderDTO.getProducts()) {
      var productStock = productStockRepository.findByName(product.getName())
          .orElseThrow(() -> {
            log.info("The product with name {}} was not found", product.getName());
            return new ProductNotFoundException(String.format("The product with name %s was not found", product.getName()));
          });
      if (productStock.getQuantity() < product.getQuantity()) {
        log.info("The order could not be created because the product {} is out of stock", product.getName());
        throw new OutOfStockException(String.format("The order could not be created because the product %s is out of stock", product.getName()));
      }
    }
    return OrderMapper.mapEntityToDto(orderRepository.save(OrderMapper.mapDtoToEntity(orderDTO, buyer)));
  }

  public OrderDTO updateOrder(OrderDTO orderDTO) {
    var orderToUpdate = findOrderById(orderDTO.getId());
    for (ProductDTO product : orderDTO.getProducts()) {
      var productStock = productStockRepository.findByName(product.getName())
          .orElseThrow(() -> {
            log.info("The product with name {}} was not found", product.getName());
            return new ProductNotFoundException(String.format("The product with name %s was not found", product.getName()));
          });
      if (productStock.getQuantity() < product.getQuantity()) {
        log.info("The order could not be created because the product {} is out of stock", product.getName());
        throw new OutOfStockException(String.format("The order could not be updated because the product %s is out of stock", product.getName()));
      } else {
        orderToUpdate.setProducts(orderDTO.getProducts().stream()
            .map(ProductMapper::mapDtoToEntity)
            .collect(Collectors.toList()));
      }
    }

    return OrderMapper.mapEntityToDto(orderRepository.save(orderToUpdate));
  }

  public Order findOrderById(Long id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> {
          log.info("The order with id {} does not exist", id);
          return new OrderNotFoundException(String.format("The order with id %s does not exist", id));
        });
  }

  public List<OrderDTO> findBuyerOrders(Authentication auth) {
    String[] names = ((String)auth.getPrincipal()).split("(?=\\p{Upper})");
    return Stream.of(orderRepository.findByBuyerFirstNameAndLastName(names[0], names[1]))
        .flatMap(Collection::stream)
        .map(OrderMapper::mapEntityToDto).toList();
  }

  public void deleteOrderById(Long id) {
    try {
      orderRepository.deleteById(id);
    } catch (EmptyResultDataAccessException ex) {
      log.info("The order with id {} does not exist", id);
      throw new OrderNotFoundException(String.format("The order with id %s does not exist", id));
    }
  }

  @Transactional
  public void deleteUserOrders(String username) {
    var buyer = buyerService.findBuyerByUsername(username);
    try {
      orderRepository.deleteByBuyer(buyer);
    } catch (EmptyResultDataAccessException ex) {
      log.info("The buyer {} does not have any orders", buyer.getUsername());
      throw new OrderNotFoundException(String.format("The buyer %s does not have any orders", buyer.getUsername()));
    }
  }
}
