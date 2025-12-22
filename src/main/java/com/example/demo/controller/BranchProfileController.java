package com.example.demo.controller;

import com.example.demo.entity.BranchProfile;
import com.example.demo.service.BranchProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchProfileController {

    private final BranchProfileService branchProfileService;

    // Dependency Injection
    public BranchProfileController(BranchProfileService branchProfileService) {
        this.branchProfileService = branchProfileService;
    }

    @PostMapping
    public BranchProfile createBranch(@RequestBody BranchProfile branch) {
        return branchProfileService.createBranch(branch);
    }

    @PutMapping("/{id}/status")
    public BranchProfile updateBranchStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {

        return branchProfileService.updateBranchStatus(id, active);
    }
    @GetMapping("/{id}")
    public BranchProfile getBranchById(@PathVariable Long id) {
        return branchProfileService.getBranchById(id);
    }
    @GetMapping
    public List<BranchProfile> getAllBranches() {
        return branchProfileService.getAllBranches();
    }
    @GetMapping("/lookup/{branchCode}")
    public BranchProfile findByBranchCode(@PathVariable String branchCode) {
        return branchProfileService.findByBranchCode(branchCode);
    }
}

