package com.assignment.storemanagementtool;

import com.assignment.storemanagementtool.dto.ProductStockDTO;
import com.assignment.storemanagementtool.entity.ProductStock;
import com.assignment.storemanagementtool.repository.ProductStockRepository;
import com.assignment.storemanagementtool.service.ProductStockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductStockServiceTest {

  @InjectMocks
  private ProductStockService productStockService;
  @Mock
  private ProductStockRepository productStockRepository;

  @Test
  public void should_add_stock() {
    ProductStockDTO productStockDTO = new ProductStockDTO();
    productStockDTO.setName("chocolate");
    productStockDTO.setQuantity(100);

    ProductStock productStock = new ProductStock();
    productStock.setName("chocolate");
    productStock.setQuantity(100);
    when(productStockRepository.save(productStock)).thenReturn(productStock);

    ProductStockDTO expected = productStockService.addStock(productStockDTO);
    Assertions.assertEquals(100, expected.getQuantity());
    Assertions.assertEquals("chocolate", expected.getName());
  }

  @Test
  public void should_update_stock() {
    ProductStockDTO productStockDTO = new ProductStockDTO();
    productStockDTO.setName("chocolate");
    productStockDTO.setQuantity(300);

    ProductStock productStock = new ProductStock();
    productStock.setName("chocolate");
    productStock.setQuantity(100);
    when(productStockRepository.findByName("chocolate")).thenReturn(Optional.of(productStock));
    when(productStockRepository.save(productStock)).thenReturn(productStock);

    ProductStockDTO expected = productStockService.updateStock(productStockDTO);
    Assertions.assertEquals(300, expected.getQuantity());
    Assertions.assertEquals("chocolate", expected.getName());
  }

  @Test
  public void should_find_product_stock_by_name() {
    ProductStock productStock = new ProductStock();
    productStock.setName("chocolate");
    productStock.setQuantity(100);
    when(productStockRepository.findByName("chocolate")).thenReturn(Optional.of(productStock));

    ProductStockDTO expected = productStockService.findStockByName("chocolate");
    Assertions.assertEquals(100, expected.getQuantity());
    Assertions.assertEquals("chocolate", expected.getName());
  }
}
