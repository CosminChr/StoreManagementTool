package com.assignment.storemanagementtool.exception;

public class ProductStockNotFoundException extends RuntimeException {
  public ProductStockNotFoundException(String message) {
    super(message);
  }
}
