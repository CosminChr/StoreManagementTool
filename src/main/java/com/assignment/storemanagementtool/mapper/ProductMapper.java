package com.assignment.storemanagementtool.mapper;


import com.assignment.storemanagementtool.dto.ProductDTO;
import com.assignment.storemanagementtool.entity.Product;

public class ProductMapper {

  public static Product mapDtoToEntity(ProductDTO productDTO) {
    Product product = new Product();
    product.setId(productDTO.getId());
    product.setName(productDTO.getName());
    product.setPrice(productDTO.getPrice());
    product.setQuantity(productDTO.getQuantity());
    return product;
  }

  public static ProductDTO mapEntityToDto(Product product) {
    ProductDTO productDto = new ProductDTO();
    productDto.setId(product.getId());
    productDto.setName(product.getName());
    productDto.setPrice(product.getPrice());
    productDto.setQuantity(product.getQuantity());
    return productDto;
  }
}
