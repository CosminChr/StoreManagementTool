package com.assignment.storemanagementtool.mapper;

import com.assignment.storemanagementtool.dto.UserDTO;
import com.assignment.storemanagementtool.entity.Buyer;

public class BuyerMapper {

  public static Buyer mapDtoToEntity(UserDTO userDTO) {
    Buyer buyer = new Buyer();
    buyer.setId(userDTO.getId());
    buyer.setEmail(userDTO.getEmail());
    buyer.setFirstName(userDTO.getFirstName());
    buyer.setLastName(userDTO.getLastName());
    buyer.setPassword(userDTO.getPassword());
    return buyer;
  }
}
