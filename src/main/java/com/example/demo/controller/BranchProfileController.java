package com.example.demo.controller;

import com.example.demo.entity.BranchProfile;
import com.example.demo.service.BranchProfileService;
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
    public BranchProfile createBranch(@RequestBody BranchProfile branchProfile) {
        return branchProfileService.createBranch(branchProfile);
    }
        @PutMapping("/{id}/status")
    public BranchProfile updateBranchStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {

        return branchProfileService.updateStatus(id, active);
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
    public BranchProfile getByBranchCode(@PathVariable String branchCode) {
        return branchProfileService.getByBranchCode(branchCode);
    }

