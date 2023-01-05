package com.assignment.storemanagementtool.controller;

import com.assignment.storemanagementtool.dto.ProductStockDTO;
import com.assignment.storemanagementtool.service.ProductStockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/stock")
@AllArgsConstructor
@Secured("ROLE_ADMIN")
@Slf4j
public class ProductStockController {

  private ProductStockService productStockService;

  @GetMapping("")
  public ResponseEntity<ProductStockDTO> findStockByName(@QueryParam("name") String name) {
    var productStock = productStockService.findStockByName(name);
    log.info("The product stock {} was successfully retrieved", productStock.getName());
    return ResponseEntity.ok(productStock);
  }

  @PostMapping("")
  public ResponseEntity<ProductStockDTO> addStock(@RequestBody ProductStockDTO productStockDTO) {
    var productStock = productStockService.addStock(productStockDTO);
    log.info("The product stock  {} was successfully created", productStock.getName());
    return ResponseEntity.status(201).body(productStock);
  }

  @PutMapping("")
  public ResponseEntity<ProductStockDTO> updateStock(@RequestBody ProductStockDTO productStockDTO) {
    var productStock = productStockService.updateStock(productStockDTO);
    log.info("The product stock was successfully updated");
    return ResponseEntity.ok(productStock);
  }
}
