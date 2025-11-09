package com.edusmart.service.ui;

import com.edusmart.dto.JwtResponseDTO;
import com.edusmart.dto.LoginRequestDTO;
import com.edusmart.dto.UserDTO;
import com.edusmart.service.AuthService;
import com.edusmart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UIAuthenticationService {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    public JwtResponseDTO login(String mail,String password){
        LoginRequestDTO loginRequestDTO=new LoginRequestDTO();
        loginRequestDTO.setEmail(mail);
        loginRequestDTO.setPassword(password);
        return authService.login(loginRequestDTO);
    }
    public UserDTO register(UserDTO userDTO){
        return userService.createUser(userDTO);
    }
}
