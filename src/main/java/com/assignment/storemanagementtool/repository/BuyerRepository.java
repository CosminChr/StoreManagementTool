package com.assignment.storemanagementtool.repository;

import com.assignment.storemanagementtool.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
  Optional<Buyer> findByFirstNameAndLastName(String firstName, String lastName);
}
