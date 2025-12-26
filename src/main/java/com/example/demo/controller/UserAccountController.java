package com.example.demo.controller;

import com.example.demo.entity.UserAccount;
import com.example.demo.service.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "User Account Management")
public class UserAccountController {

    private final UserAccountService userService;

    public UserAccountController(UserAccountService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users (ADMIN only)")
    public List<UserAccount> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by ID (ADMIN only)")
    public UserAccount getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
