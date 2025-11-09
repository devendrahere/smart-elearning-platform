package com.edusmart.service;

import com.edusmart.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long userId);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long userId, UserDTO updatedUser);
    void deleteUser(Long userId);
    UserDTO getUserByUsername(String username);
}
