package com.example.demo.controller;

import com.example.demo.entity.BranchProfile;
import com.example.demo.service.impl.BranchProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchProfileController {

    @Autowired
    private BranchProfileServiceImpl branchProfileService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('REVIEWER')")
    public ResponseEntity<List<BranchProfile>> getAllBranches() {
        return ResponseEntity.ok(branchProfileService.getAllBranches());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BranchProfile> createBranch(@RequestBody BranchProfile branch) {
        return ResponseEntity.ok(branchProfileService.createBranch(branch));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BranchProfile> updateBranchStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(branchProfileService.updateBranchStatus(id, active));
    }
}
