package com.assignment.storemanagementtool.mapper;

import com.assignment.storemanagementtool.dto.BuyerDTO;
import com.assignment.storemanagementtool.entity.Buyer;

public class BuyerMapper {

  public static Buyer mapDtoToEntity(BuyerDTO buyerDTO) {
    var buyer = new Buyer();
    buyer.setId(buyerDTO.getId());
    buyer.setEmail(buyerDTO.getEmail());
    buyer.setFirstName(buyerDTO.getFirstName());
    buyer.setLastName(buyerDTO.getLastName());
    buyer.setPassword(buyerDTO.getPassword());
    return buyer;
  }
}
