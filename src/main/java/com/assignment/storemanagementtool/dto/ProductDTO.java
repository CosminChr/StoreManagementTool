package com.assignment.storemanagementtool.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {
  private Long id;
  private String name;
  private int quantity;
  private BigDecimal price;
}
