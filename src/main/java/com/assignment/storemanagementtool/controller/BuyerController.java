package com.assignment.storemanagementtool.controller;

import com.assignment.storemanagementtool.dto.BuyerDTO;
import com.assignment.storemanagementtool.service.BuyerService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyers")
@AllArgsConstructor
public class BuyerController {

  private BuyerService buyerService;

  @PostMapping("/register")
  public UserDetails register(@RequestBody BuyerDTO buyerDTO) {
    return buyerService.register(buyerDTO);
  }

  @Secured("ROLE_BUYER")
  @GetMapping("/hello")
  public String hello(@RequestBody BuyerDTO buyerDTO) {
    return "test";
  }
}
