package com.assignment.storemanagementtool.repository;


import com.assignment.storemanagementtool.entity.Order;
import com.assignment.storemanagementtool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  public List<Order> findByUser(User user);
  public List<Order> deleteByUser(User user);
}
