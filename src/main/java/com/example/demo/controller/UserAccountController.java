package com.example.demo.controller;

import com.example.demo.entity.UserAccount;
import com.example.demo.service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserAccount>> getAllUsers() {
        return ResponseEntity.ok(userAccountService.getAllUsers());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserAccount> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userAccountService.getUserById(id));
    }

    // Create user
    @PostMapping
    public ResponseEntity<UserAccount> createUser(@RequestBody UserAccount userAccount) {
        return ResponseEntity.ok(userAccountService.createUser(userAccount));
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<UserAccount> updateUser(
            @PathVariable Long id,
            @RequestBody UserAccount userAccount) {
        return ResponseEntity.ok(userAccountService.updateUser(id, userAccount));
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userAccountService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
