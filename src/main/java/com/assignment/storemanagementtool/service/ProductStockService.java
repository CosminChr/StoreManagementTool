package com.assignment.storemanagementtool.service;

import com.assignment.storemanagementtool.dto.ProductStockDTO;
import com.assignment.storemanagementtool.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class ProductStockService {

  private ProductStockRepository productStockRepository;

  public ProductStockDTO addStock(ProductStockDTO productStockDTO) {
   return null;
  }

  public ProductStockDTO findStockByName(String name) {
   return null;
  }

  public ProductStockDTO updateStock(ProductStockDTO productStockDTO) {
    return null;
  }
}
