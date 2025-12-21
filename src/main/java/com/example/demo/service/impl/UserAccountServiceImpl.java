package com.example.demo.service.impl;

import com.example.demo.entity.UserAccount;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.service.UserAccountService;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹 Register user
    @Override
    public UserAccount register(UserAccount user) {

        if (userAccountRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Email already in use");
        }

        if (user.getRole() == null) {
            user.setRole("REVIEWER");
        }

        // Hash password
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userAccountRepository.save(user);
    }

    // 🔹 Find user by email
    @Override
    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // 🔹 Get user by ID
    @Override
    public UserAccount getUser(Long id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // 🔹 Get all users
    @Override
    public List<UserAccount> getAllUsers() {
        return userAccountRepository.findAll();
    }
}
