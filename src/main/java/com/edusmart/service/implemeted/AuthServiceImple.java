package com.edusmart.service.implemeted;

import com.edusmart.dto.JwtResponseDTO;
import com.edusmart.dto.LoginRequestDTO;
import com.edusmart.dto.UserDTO;
import com.edusmart.entity.Roles;
import com.edusmart.entity.Users;
import com.edusmart.repository.UserRepository;
import com.edusmart.security.JwtTokenProvider;
import com.edusmart.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImple implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public JwtResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        Users user =userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new RuntimeException("Email is not registered"));
        List<String> roles=user.getRoles()
                .stream()
                .map(Roles::getName)
                .toList();

        String token= jwtTokenProvider.generateToken(userDetails,user.getUserId(),roles);

        JwtResponseDTO jwtResponseDTO=new JwtResponseDTO();
        jwtResponseDTO.setToken(token);
        jwtResponseDTO.setUserId(user.getUserId());
        jwtResponseDTO.setEmail(user.getEmail());
        jwtResponseDTO.setRoles(roles);

        return jwtResponseDTO;
    }

    @Override
    public JwtResponseDTO refreshToken(String refreshToken) {
        return null;
    }
}
