package com.assignment.storemanagementtool.repository;



import com.assignment.storemanagementtool.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
  Optional<ProductStock> findByName(String name);
}
