package com.assignment.storemanagementtool.repository;


import com.assignment.storemanagementtool.entity.Buyer;
import com.assignment.storemanagementtool.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  @Query(value = "SELECT o FROM Order o WHERE o.buyer.firstName = :firstName AND o.buyer.lastName = :lastName ")
  List<Order> findByBuyerFirstNameAndLastName(String firstName, String lastName);
  List<Order> deleteByBuyer(Buyer buyer);
}
