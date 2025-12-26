package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAccount;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserAccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAccountService userAccountService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserAccountService userAccountService,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.userAccountService = userAccountService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public UserAccount register(@RequestBody RegisterRequest req) {

        UserAccount user = new UserAccount();
        user.setFullName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setRole(req.getRole());
        user.setDepartment(req.getDepartment());

        return userAccountService.register(user);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest req) {

        UserAccount user = userAccountService.findByEmail(req.getEmail());

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateTokenForUser(user);

        return Map.of(
                "token", token,
                "user", user
        );
    }

    @GetMapping("/users")
    public Object getAllUsers() {
        return userAccountService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserAccount getUser(@PathVariable Long id) {
        return userAccountService.getUser(id);
    }
}
