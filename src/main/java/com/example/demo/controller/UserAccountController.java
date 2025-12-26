package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAccount;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class UserAccountController {

    private final UserAccountService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public UserAccountController(UserAccountService userService,
                                 JwtUtil jwtUtil,
                                 PasswordEncoder encoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    @Operation(summary = "Registers a new user")
    public UserAccount register(@RequestBody RegisterRequest req) {
        UserAccount user = new UserAccount(
                null,
                req.getName(),
                req.getEmail(),
                req.getPassword(),
                req.getRole(),
                req.getDepartment(),
                null
        );
        return userService.register(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticates and returns JWT")
    public Map<String, Object> login(@RequestBody LoginRequest req) {

        UserAccount user = userService.findByEmail(req.getEmail());

        if (!encoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        String token = jwtUtil.generateTokenForUser(user);

        return Map.of(
                "token", token,
                "email", user.getEmail(),
                "role", user.getRole(),
                "userId", user.getId()
        );
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users (ADMIN only)")
    public List<UserAccount> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by ID (ADMIN only)")
    public UserAccount get(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
