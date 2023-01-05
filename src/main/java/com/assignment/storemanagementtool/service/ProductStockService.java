package com.assignment.storemanagementtool.service;

import com.assignment.storemanagementtool.dto.ProductStockDTO;
import com.assignment.storemanagementtool.exception.ProductStockNotFoundException;
import com.assignment.storemanagementtool.mapper.ProductStockMapper;
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
    var productStock = productStockRepository.save(ProductStockMapper.mapDtoToEntity(productStockDTO));
    return ProductStockMapper.mapEntityToDto(productStock);
  }

  public ProductStockDTO findStockByName(String name) {
    return ProductStockMapper.mapEntityToDto(productStockRepository.findByName(name)
        .orElseThrow(() -> {
          log.info("The product stock with name {}} was not found", name);
          return new ProductStockNotFoundException(String.format("The product stock with name %s was not found", name));
        }));
  }

  public ProductStockDTO updateStock(ProductStockDTO productStockDTO) {
    var stockToUpdate = productStockRepository.findByName(productStockDTO.getName())
        .orElseThrow(() -> {
          log.info("The product stock with name {}} was not found", productStockDTO.getName());
          return new ProductStockNotFoundException(String.format("The product stock with name %s was not found", productStockDTO.getName()));
        });
    stockToUpdate.setName(productStockDTO.getName());
    stockToUpdate.setQuantity(productStockDTO.getQuantity());

    return ProductStockMapper.mapEntityToDto(productStockRepository.save(stockToUpdate));
  }
}
