package com.edusmart.service;

import com.edusmart.dto.JwtResponseDTO;
import com.edusmart.dto.LoginRequestDTO;
import com.edusmart.dto.UserDTO;

public interface AuthService {
    UserDTO registerUser(UserDTO userDTO);
    JwtResponseDTO login(LoginRequestDTO loginRequest);
    JwtResponseDTO refreshToken(String refreshToken);
}
