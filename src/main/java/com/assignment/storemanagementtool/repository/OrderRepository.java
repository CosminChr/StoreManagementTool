package com.assignment.storemanagementtool.repository;


import com.assignment.storemanagementtool.entity.Buyer;
import com.assignment.storemanagementtool.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  public List<Order> findByBuyer(Buyer buyer);
  public List<Order> deleteByBuyer(Buyer buyer);
}
