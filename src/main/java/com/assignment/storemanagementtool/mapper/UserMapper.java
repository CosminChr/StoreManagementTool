package com.assignment.storemanagementtool.mapper;

import com.assignment.storemanagementtool.dto.UserDTO;
import com.assignment.storemanagementtool.entity.User;

public class UserMapper {

  public static User mapDtoToEntity(UserDTO userDTO) {
    User user = new User();
    user.setId(userDTO.getId());
    user.setEmail(userDTO.getEmail());
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setPassword(userDTO.getPassword());
    return user;
  }
}
