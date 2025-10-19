package com.edusmart.controller;

import com.edusmart.dto.UserDTO;
import com.edusmart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //creating user
    //body UserDTO
    //url api/users/
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        UserDTO user=userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    //fetching the user by user id
    //request parameter is userId
    //url api/users/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        UserDTO user= userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }


    //fetching all  users
    //request nothing as fetching all users
    //url api/users/
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //updating the user
    //using @PutMapping to update the details
    //url api/users/{userId}
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId,@RequestBody UserDTO updatedUser){
        UserDTO user= userService.updateUser(userId,updatedUser);
        return ResponseEntity.ok(user);
    }

    //removing the user from the database
    //using @DeleteMapping annotation
    //url api/users/{userId}
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
