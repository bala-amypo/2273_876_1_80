package com.example.demo.service.impl;

import com.example.demo.entity.UserAccount;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.service.UserAccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository repo;
    private final PasswordEncoder encoder;

    public UserAccountServiceImpl(UserAccountRepository repo,
                                  PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UserAccount register(UserAccount user) {

        if (repo.existsByEmail(user.getEmail()))
            throw new ValidationException("Email already in use");

        if (user.getPassword() == null || user.getPassword().length() < 8)
            throw new ValidationException("Password must be at least 8 characters");

        user.setPassword(encoder.encode(user.getPassword()));

        if (user.getRole() == null)
            user.setRole("REVIEWER");

        return repo.save(user);
    }

    @Override
    public UserAccount getUser(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserAccount> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public UserAccount findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
