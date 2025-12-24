package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAccount;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // ----------------- REGISTER -----------------
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        UserAccount user = new UserAccount();
        user.setFullName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setDepartment(request.getDepartment());

        UserAccount createdUser = userAccountService.register(user);

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully", createdUser));
    }

    // ----------------- LOGIN -----------------
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        UserAccount user = userAccountService.findByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Invalid email or password"));
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);

        return ResponseEntity.ok(new ApiResponse(true, "Login successful", data));
    }

    // ----------------- GET ALL USERS (ADMIN) -----------------
    @GetMapping("/users")
    public ResponseEntity<List<UserAccount>> getAllUsers() {
        List<UserAccount> users = userAccountService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // ----------------- GET USER BY ID (ADMIN) -----------------
    @GetMapping("/users/{id}")
    public ResponseEntity<UserAccount> getUser(@PathVariable Long id) {
        UserAccount user = userAccountService.getUser(id);
        return ResponseEntity.ok(user);
    }
}
