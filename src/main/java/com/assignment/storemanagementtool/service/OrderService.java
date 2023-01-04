package com.assignment.storemanagementtool.service;

import com.assignment.storemanagementtool.dto.OrderDTO;
import com.assignment.storemanagementtool.dto.ProductDTO;
import com.assignment.storemanagementtool.dto.BuyerDTO;
import com.assignment.storemanagementtool.entity.Buyer;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class OrderService {

  private OrderRepository orderRepository;
  private ProductStockRepository productStockRepository;
  private BuyerService buyerService;

  public OrderDTO createOrder(OrderDTO orderDTO, Authentication auth) {
    Buyer buyer = buyerService.findBuyerById(String.valueOf(auth.getPrincipal()));
    for (ProductDTO product : orderDTO.getProducts()) {
      ProductStock productStock = productStockRepository.findByName(product.getName())
          .orElseThrow(() -> new ProductNotFoundException(String.format("The product with name %s was not found", product.getName())));
      if (productStock.getQuantity() < product.getQuantity()) {
        throw new OutOfStockException(String.format("The order could not be created because the product %s is out of stock", product.getName()));
      }
    }
    return OrderMapper.mapEntityToDto(orderRepository.save(OrderMapper.mapDtoToEntity(orderDTO, buyer)));
  }

  public OrderDTO updateOrder(OrderDTO orderDTO) {
    Order orderToUpdate = findOrderById(orderDTO.getId());
    for (ProductDTO product : orderDTO.getProducts()) {
      ProductStock productStock = productStockRepository.findByName(product.getName())
          .orElseThrow(() -> new ProductNotFoundException(String.format("The product with name %s was not found", product)));
      if (productStock.getQuantity() < product.getQuantity()) {
        throw new OutOfStockException(String.format("The order could not be updated because the product %s is out of stock", product.getName()));
      }
    }

    return OrderMapper.mapEntityToDto(orderRepository.save(orderToUpdate));
  }

  public Order findOrderById(Long id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(String.format("The order with id %s does not exist", id)));
  }

  public List<OrderDTO> findUserOrders(Authentication auth) {
    String[] names = ((String)auth.getPrincipal()).split("(?=\\p{Upper})");
    return Stream.of(orderRepository.findByBuyerFirstNameAndLastName(names[0], names[1]))
        .flatMap(Collection::stream)
        .map(OrderMapper::mapEntityToDto).toList();
  }

  public void deleteOrderById(Long id) {
    orderRepository.deleteById(id);
  }

  public void deleteUserOrders(BuyerDTO buyerDTO) {
    orderRepository.deleteByBuyer(BuyerMapper.mapDtoToEntity(buyerDTO));
  }
}
