package com.assignment.storemanagementtool.repository;

import com.assignment.storemanagementtool.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}
