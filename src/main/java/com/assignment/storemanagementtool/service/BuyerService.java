package com.assignment.storemanagementtool.service;

import com.assignment.storemanagementtool.dto.BuyerDTO;
import com.assignment.storemanagementtool.entity.Buyer;
import com.assignment.storemanagementtool.exception.UserNotFoundException;
import com.assignment.storemanagementtool.mapper.BuyerMapper;
import com.assignment.storemanagementtool.repository.BuyerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BuyerService {

  private UserDetailsService userDetailsService;
  private BuyerRepository buyerRepository;
  private PasswordEncoder passwordEncoder;

  public UserDetails register(BuyerDTO buyerDTO) {
    Buyer buyer = BuyerMapper.mapDtoToEntity(buyerDTO);
    buyer.setPassword(passwordEncoder.encode(buyer.getPassword()));
    buyerRepository.save(buyer);
    ((UserDetailsManager)(userDetailsService)).createUser(buyer);
    return userDetailsService.loadUserByUsername(String.join("", buyer.getFirstName(), buyer.getLastName()));
  }

  public Buyer findBuyerByUsername(String username) {
    String[] names = username.split("(?=\\p{Upper})");
    return buyerRepository.findByFirstNameAndLastName(names[0], names[1])
        .orElseThrow(() -> new UserNotFoundException(String.format("The user %s could not be found", username)));
    }
}
