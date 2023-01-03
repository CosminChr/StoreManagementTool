package com.assignment.storemanagementtool;

import com.assignment.storemanagementtool.dto.OrderDTO;
import com.assignment.storemanagementtool.dto.ProductDTO;
import com.assignment.storemanagementtool.dto.UserDTO;
import com.assignment.storemanagementtool.entity.Buyer;
import com.assignment.storemanagementtool.entity.Order;
import com.assignment.storemanagementtool.entity.Product;
import com.assignment.storemanagementtool.entity.ProductStock;
import com.assignment.storemanagementtool.exception.OrderNotFoundException;
import com.assignment.storemanagementtool.exception.OutOfStockException;
import com.assignment.storemanagementtool.repository.OrderRepository;
import com.assignment.storemanagementtool.repository.ProductStockRepository;

import com.assignment.storemanagementtool.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

  @InjectMocks
  private OrderService systemUnderTest;
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private ProductStockRepository productStockRepository;

  @Test
  public void should_create_order() {
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setOrderTime(LocalDateTime.now());
    ProductDTO productDTO = new ProductDTO();
    productDTO.setPrice(BigDecimal.valueOf(100));
    productDTO.setName("milk");
    productDTO.setQuantity(1);
    orderDTO.setProducts(List.of(productDTO));
    UserDTO userDTO = new UserDTO();
    orderDTO.setUser(userDTO);

    Order order = new Order();
    order.setId(1L);
    order.setOrderTime(LocalDateTime.now());
    Product product = new Product();
    product.setPrice(BigDecimal.valueOf(100));
    product.setName("milk");
    product.setQuantity(1);
    order.setProducts(List.of(product));

    ProductStock productStock = new ProductStock();
    productStock.setId(1L);
    productStock.setQuantity(1);
    productStock.setName("milk");

    when(productStockRepository.findByName("milk")).thenReturn(Optional.of(productStock));
    when(orderRepository.save(any())).thenReturn(order);

    Order createdOrder = systemUnderTest.createOrder(orderDTO);

    assertEquals(1, createdOrder.getProducts().size());
    assertEquals("milk", createdOrder.getProducts().get(0).getName());
    assertEquals(1, createdOrder.getProducts().get(0).getQuantity());
    assertEquals(BigDecimal.valueOf(100), createdOrder.getProducts().get(0).getPrice());
  }

  @Test
  public void should_throw_when_product_is_out_of_stock() {
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setOrderTime(LocalDateTime.now());
    ProductDTO productDTO = new ProductDTO();
    productDTO.setPrice(BigDecimal.valueOf(100));
    productDTO.setName("milk");
    productDTO.setQuantity(2);
    orderDTO.setProducts(List.of(productDTO));

    ProductStock productStock = new ProductStock();
    productStock.setId(1L);
    productStock.setQuantity(1);
    productStock.setName("milk");

    when(productStockRepository.findByName("milk")).thenReturn(Optional.of(productStock));

    Throwable exception = assertThrows(OutOfStockException.class, () -> systemUnderTest.createOrder(orderDTO));
    assertEquals(String.format("The order could not be created because the product %s is out of stock", "milk"), exception.getMessage());
  }

  @Test
  public void should_update_order() {
    Order order = new Order();
    order.setId(1L);
    order.setOrderTime(LocalDateTime.now());
    Product product = new Product();
    product.setPrice(BigDecimal.valueOf(100));
    product.setName("milk");
    product.setQuantity(1);
    order.setProducts(List.of(product));

    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setOrderTime(LocalDateTime.now());
    ProductDTO productDTO = new ProductDTO();
    productDTO.setPrice(BigDecimal.valueOf(200));
    productDTO.setName("milk");
    productDTO.setQuantity(2);
    orderDTO.setProducts(List.of(productDTO));
    orderDTO.setId(1L);

    ProductStock productStock = new ProductStock();
    productStock.setId(1L);
    productStock.setQuantity(3);
    productStock.setName("milk");
    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(productStockRepository.findByName("milk")).thenReturn(Optional.of(productStock));
    order.getProducts().get(0).setQuantity(2);
    order.getProducts().get(0).setPrice(BigDecimal.valueOf(200));
    when(orderRepository.save(any())).thenReturn(order);
    Order updatedOrder = systemUnderTest.updateOrder(orderDTO);

    assertEquals(1, updatedOrder.getProducts().size());
    assertEquals("milk", updatedOrder.getProducts().get(0).getName());
    assertEquals(2, updatedOrder.getProducts().get(0).getQuantity());
    assertEquals(BigDecimal.valueOf(200), updatedOrder.getProducts().get(0).getPrice());
  }

  @Test
  public void should_find_order_by_its_id() {
    Order order = new Order();
    order.setId(1L);
    order.setOrderTime(LocalDateTime.now());
    Product product = new Product();
    product.setPrice(BigDecimal.valueOf(100));
    product.setName("milk");
    product.setQuantity(1);
    order.setProducts(List.of(product));

    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

    Order foundOrder = systemUnderTest.findOrderById(1L);

    assertEquals(1, foundOrder.getProducts().size());
    assertEquals("milk", foundOrder.getProducts().get(0).getName());
    assertEquals(1, foundOrder.getProducts().get(0).getQuantity());
    assertEquals(BigDecimal.valueOf(100), foundOrder.getProducts().get(0).getPrice());
  }

  @Test
  public void should_throw_when_order_does_not_exist() {

    when(orderRepository.findById(1L)).thenReturn(Optional.empty());

    Throwable exception = assertThrows(OrderNotFoundException.class, () -> systemUnderTest.findOrderById(1L));
    assertEquals(String.format("The order with id %s does not exist", 1), exception.getMessage());
  }

  @Test
  public void should_find_user_orders() {

    Buyer buyer = new Buyer();
    buyer.setId(1L);

    Order order = new Order();
    order.setId(1L);
    order.setOrderTime(LocalDateTime.now());
    Product product = new Product();
    product.setPrice(BigDecimal.valueOf(100));
    product.setName("milk");
    product.setQuantity(1);
    order.setProducts(List.of(product));

    UserDTO userDTO = new UserDTO();
    userDTO.setId(1L);

    when(orderRepository.findByBuyer(buyer)).thenReturn(List.of(order));

    List<Order> userOrders = systemUnderTest.findUserOrders(userDTO);
    assertEquals(1, userOrders.get(0).getProducts().size());
    assertEquals("milk", userOrders.get(0).getProducts().get(0).getName());
    assertEquals(1, userOrders.get(0).getProducts().get(0).getQuantity());
    assertEquals(BigDecimal.valueOf(100), userOrders.get(0).getProducts().get(0).getPrice());

  }

  @Test
  public void should_delete_order_by_id() {
    systemUnderTest.deleteOrderById(1L);
    verify(orderRepository).deleteById(1L);
  }

  @Test
  public void should_delete_user_orders() {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(1L);

    Buyer buyer = new Buyer();
    buyer.setId(1L);

    systemUnderTest.deleteUserOrders(userDTO);
    verify(orderRepository).deleteByBuyer(buyer);
  }
}