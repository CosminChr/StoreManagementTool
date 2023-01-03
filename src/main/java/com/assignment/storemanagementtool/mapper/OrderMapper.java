package com.assignment.storemanagementtool.mapper;


import com.assignment.storemanagementtool.dto.OrderDTO;
import com.assignment.storemanagementtool.entity.Order;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class OrderMapper {

  public static Order mapDtoToEntity(OrderDTO orderDTO) {
    Order order = new Order();
    order.setOrderTime(LocalDateTime.now());
    order.setProducts(orderDTO.getProducts().stream().map(ProductMapper::mapDtoToEntity).collect(Collectors.toList()));
    order.setUser(UserMapper.mapDtoToEntity(orderDTO.getUser()));
    return order;
  }
}
