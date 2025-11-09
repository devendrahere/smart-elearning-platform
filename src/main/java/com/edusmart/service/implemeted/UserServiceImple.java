package com.edusmart.service.implemeted;

import com.edusmart.dto.RoleDTO;
import com.edusmart.dto.UserDTO;
import com.edusmart.entity.Roles;
import com.edusmart.entity.Users;
import com.edusmart.exception.ResourcesNotFound;
import com.edusmart.repository.RoleRepository;
import com.edusmart.repository.UserRepository;
import com.edusmart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail()))
                throw  new ResourcesNotFound("Email already registered");

        Users user =mapToEntity(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        Users saved=userRepository.save(user);
        return mapToDTO(saved);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        Users users=userRepository.findById(userId).
                orElseThrow(()-> new ResourcesNotFound("User Not Found"));
        return mapToDTO(users);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO updatedUser) {
        Users users=userRepository.findById(userId)
                .orElseThrow(()->new ResourcesNotFound("User Not Found"));
        users.setUpdatedAt(LocalDateTime.now());
        users.setUsername(updatedUser.getUsername());
        users.setEmail(updatedUser.getEmail());
        Users updated=userRepository.save(users);
        return mapToDTO(updated);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        Users user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourcesNotFound("User not found with email: " + username));
        return mapToDTO(user);
    }
    //mapping helpers

    private Users mapToEntity(UserDTO dto){
        Users users=new Users();
        users.setUsername(dto.getUsername());
        users.setEmail(dto.getEmail());

        Set<Roles> roles=new HashSet<>();
        if(dto.getRoles()!=null){
            dto.getRoles().forEach(r->{
                Roles role=roleRepository.findById((long) r.getRoleId())
                        .orElseThrow(()->new ResourcesNotFound("Role not Found "+r.getRoleId()));
                roles.add(role);
            });
        }
        users.setRoles(roles);
        return users;
    }


    private UserDTO mapToDTO(Users users){
        UserDTO dto=new UserDTO();
        dto.setUserId(users.getUserId());
        dto.setUsername(users.getUsername());
        dto.setEmail(users.getEmail());
        dto.setCreatedAt(users.getCreatedAt());
        dto.setUpdatedAt(users.getUpdatedAt());
        dto.setRoles(
                users.getRoles().stream()
                        .map(r->{
                            RoleDTO rdto=new RoleDTO();
                            rdto.setRoleId(r.getRoleId());
                            rdto.setName(r.getName());
                            return rdto;
                        }).collect(Collectors.toSet())
        );
        return dto;
    }
}
