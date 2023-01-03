package com.assignment.storemanagementtool.repository;

import com.assignment.storemanagementtool.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
