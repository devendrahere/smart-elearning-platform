package com.edusmart.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

public class GenerateHash {
    public static void main(String[] args) {
        String key = "k9sBv1w^z%C*F-JaNdRgUjXn2r5u8x/A?D(G+KbPeShVmYp3s6v9y$B&E)H@McQf";
        String encodedKey = Base64.getEncoder().encodeToString(key.getBytes());
        System.out.println(encodedKey);
    }
}
