package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAccount;
import com.example.demo.service.impl.UserAccountServiceImpl;
import com.example.demo.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAccountController {

    @Autowired
    private UserAccountServiceImpl userAccountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserAccount> register(@Valid @RequestBody RegisterRequest req) {
        UserAccount user = new UserAccount(req.getName(), req.getEmail(), req.getPassword(), req.getRole(), req.getDepartment());
        UserAccount created = userAccountService.register(user);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        UserAccount user = userAccountService.getByEmail(req.getEmail());
        String token = jwtUtil.generateTokenForUser(user);
        return ResponseEntity.ok(token);
    }
}
