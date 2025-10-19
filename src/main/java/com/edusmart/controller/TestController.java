package com.edusmart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint(){
        return "Public endpoint : no token Needed";
    }

    @GetMapping("/student")
    public ResponseEntity<String> studentOnly(Authentication authencation){
        return ResponseEntity.ok("Student endPoint for: "+authencation.getName());
    }
}
