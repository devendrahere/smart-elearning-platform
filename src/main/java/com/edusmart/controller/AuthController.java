package com.edusmart.controller;

import com.edusmart.dto.JwtResponseDTO;
import com.edusmart.dto.LoginRequestDTO;
import com.edusmart.dto.UserDTO;
import com.edusmart.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    //registering new user
    //url for this is /api/auth/register
    //Body : UserDTO
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){
        UserDTO registeredUser= authService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
    //logging in user
    //we use JwtResponseDTO to login
    // url for this is /api/auth/login
    //body : LoginRequestDTO
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequest){
        JwtResponseDTO jwtResponse =authService.login(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
    //refresh token
    //url for this is /api/auth/refresh
    //body is String refreshToken
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDTO> refreshToken(@RequestParam String refreshToken){
        JwtResponseDTO newToken=authService.refreshToken(refreshToken);
        return ResponseEntity.ok(newToken);
    }
}
