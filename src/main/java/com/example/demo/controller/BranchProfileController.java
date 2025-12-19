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

    // 1️⃣ POST /api/branches
    // 🔐 Access: Protected by JWT
    @PostMapping
    public BranchProfile createBranch(@RequestBody BranchProfile branch) {
        return branchProfileService.createBranch(branch);
    }

    // 2️⃣ PUT /api/branches/{id}/status
    // 🔐 Access: Protected by JWT
    @PutMapping("/{id}/status")
    public BranchProfile updateBranchStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {

        return branchProfileService.updateBranchStatus(id, active);
    }

    // 3️⃣ GET /api/branches/{id}
    // 🔐 Access: Protected by JWT
    @GetMapping("/{id}")
    public BranchProfile getBranchById(@PathVariable Long id) {
        return branchProfileService.getBranchById(id);
    }

    // 4️⃣ GET /api/branches
    // 🔐 Access: Protected by JWT
    @GetMapping
    public List<BranchProfile> getAllBranches() {
        return branchProfileService.getAllBranches();
    }

    // 5️⃣ GET /api/branches/lookup/{branchCode}
    // 🔐 Access: Protected by JWT
    @GetMapping("/lookup/{branchCode}")
    public BranchProfile findByBranchCode(@PathVariable String branchCode) {
        return branchProfileService.findByBranchCode(branchCode);
    }
}

