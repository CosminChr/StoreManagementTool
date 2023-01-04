package com.assignment.storemanagementtool.mapper;


import com.assignment.storemanagementtool.dto.OrderDTO;
import com.assignment.storemanagementtool.entity.Buyer;
import com.assignment.storemanagementtool.entity.Order;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class OrderMapper {

  public static Order mapDtoToEntity(OrderDTO orderDTO, Buyer buyer) {
    Order order = new Order();
    order.setOrderTime(LocalDateTime.now());
    order.setProducts(orderDTO.getProducts().stream().map(ProductMapper::mapDtoToEntity).collect(Collectors.toList()));
    order.setBuyer(buyer);
    order.getProducts().forEach(prod -> prod.setOrder(order));
    return order;
  }

  public static OrderDTO mapEntityToDto(Order order) {
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setId(order.getId());
    orderDTO.setOrderTime(order.getOrderTime());
    orderDTO.setProducts(order.getProducts().stream().map(ProductMapper::mapEntityToDto).collect(Collectors.toList()));
    return orderDTO;
  }
}
