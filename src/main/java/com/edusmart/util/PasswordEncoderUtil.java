package com.edusmart.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password123"; // use the password you want to log in with
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Encoded: " + encodedPassword);
    }
}
