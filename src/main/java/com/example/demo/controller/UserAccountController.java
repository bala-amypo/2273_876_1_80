package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.UserAccount;
import com.example.demo.service.UserAccountService;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userAccountService = userAccountService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userAccountService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email is already in use"));
        }

        UserAccount user = new UserAccount();
        user.setFullName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setDepartment(request.getDepartment());

        UserAccount savedUser = userAccountService.register(user);
        return ResponseEntity.ok(savedUser);
    }

    // 2. Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        UserAccount user = userAccountService.findByEmail(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid email or password"));
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(new ApiResponse(true, "Login successful", token, user));
    }

    // 3. Get all users (ADMIN only)
    @GetMapping("/users")
    public ResponseEntity<List<UserAccount>> getAllUsers() {
        List<UserAccount> users = userAccountService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 4. Get single user by ID (ADMIN only)
    @GetMapping("/users/{id}")
    public ResponseEntity<UserAccount> getUserById(@PathVariable Long id) {
        UserAccount user = userAccountService.getUser(id);
        return ResponseEntity.ok(user);
    }
}
