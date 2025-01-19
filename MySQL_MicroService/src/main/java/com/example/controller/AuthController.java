package com.example.controller;

import com.example.filters.TokenBlacklist;
import com.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import utilizatori.Utilizator;
import utilizatori.UtilizatorRepository;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenBlacklist tokenBlacklist;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        Utilizator user = utilizatorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        System.out.println("User found: " + user.getEmail());
        System.out.println("Hashed password: " + user.getParola());

        if (!passwordEncoder.matches(request.getPassword(), user.getParola())) {
            System.out.println("Password provided: " + request.getPassword());
            System.out.println("Password match result: " + passwordEncoder.matches(request.getPassword(), user.getParola()));
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRol().name());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract the token
            tokenBlacklist.add(token); // Add the token to the blacklist
            return ResponseEntity.ok(Map.of("message", "Successfully logged out"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Authorization header is missing or invalid"));
    }


    public static class LoginRequest {
        private String email;
        private String password;

        // Getters and Setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}

