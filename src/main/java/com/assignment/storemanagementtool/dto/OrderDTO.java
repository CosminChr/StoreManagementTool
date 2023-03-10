package com.assignment.storemanagementtool.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDTO {

  private Long id;
  private LocalDateTime orderTime;
  private List<ProductDTO> products = new ArrayList<>();
}
