package com.example.demo.controller;

import com.example.demo.entity.BranchProfile;
import com.example.demo.service.BranchProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@Tag(name = "Branch Profiles")
public class BranchProfileController {
    
    private final BranchProfileService branchProfileService;
    
    public BranchProfileController(BranchProfileService branchProfileService) {
        this.branchProfileService = branchProfileService;
    }
    
    @PostMapping
    @Operation(summary = "Create branch profile")
    public ResponseEntity<BranchProfile> createBranch(@RequestBody BranchProfile branchProfile) {
        return ResponseEntity.ok(branchProfileService.createBranch(branchProfile));
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "Update branch status")
    public ResponseEntity<BranchProfile> updateBranchStatus(
            @Parameter(description = "Branch ID") @PathVariable Long id,
            @Parameter(description = "Active status") @RequestParam Boolean active) {
        return ResponseEntity.ok(branchProfileService.updateBranchStatus(id, active));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get branch by ID")
    public ResponseEntity<BranchProfile> getBranchById(
            @Parameter(description = "Branch ID") @PathVariable Long id) {
        return ResponseEntity.ok(branchProfileService.getBranchById(id));
    }
    
    @GetMapping
    @Operation(summary = "Get all branches")
    public ResponseEntity<List<BranchProfile>> getAllBranches() {
        return ResponseEntity.ok(branchProfileService.getAllBranches());
    }
    
    @GetMapping("/lookup/{branchCode}")
    @Operation(summary = "Find branch by code")
    public ResponseEntity<BranchProfile> findByBranchCode(
            @Parameter(description = "Branch code") @PathVariable String branchCode) {
        return ResponseEntity.ok(branchProfileService.findByBranchCode(branchCode));
    }
}