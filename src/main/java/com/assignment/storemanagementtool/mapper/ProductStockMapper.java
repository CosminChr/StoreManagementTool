package com.assignment.storemanagementtool.mapper;

import com.assignment.storemanagementtool.dto.ProductStockDTO;
import com.assignment.storemanagementtool.entity.ProductStock;

public class ProductStockMapper {

  public static ProductStock mapDtoToEntity(ProductStockDTO productStockDTO) {
    var productStock = new ProductStock();
    productStock.setName(productStockDTO.getName());
    productStock.setQuantity(productStockDTO.getQuantity());
    return productStock;
  }

  public static ProductStockDTO mapEntityToDto(ProductStock productStock) {
    var productStockDTO = new ProductStockDTO();
    productStockDTO.setName(productStock.getName());
    productStockDTO.setQuantity(productStock.getQuantity());
    return productStockDTO;
  }
}
