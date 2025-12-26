package com.example.demo.controller;

import com.example.demo.entity.BranchProfile;
import com.example.demo.service.BranchProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branches")
public class BranchProfileController {

    private final BranchProfileService branchProfileService;

    public BranchProfileController(BranchProfileService branchProfileService) {
        this.branchProfileService = branchProfileService;
    }

    @PostMapping
    public BranchProfile create(@RequestBody BranchProfile branch) {
        return branchProfileService.createBranch(branch);
    }

    @PutMapping("/{id}/status")
    public BranchProfile updateStatus(@PathVariable Long id,
                                      @RequestParam boolean active) {
        return branchProfileService.updateBranchStatus(id, active);
    }

    @GetMapping("/{id}")
    public BranchProfile getById(@PathVariable Long id) {
        return branchProfileService.getBranchById(id);
    }

    @GetMapping
    public Object getAll() {
        return branchProfileService.getAllBranches();
    }

    @GetMapping("/lookup/{branchCode}")
    public BranchProfile getByCode(@PathVariable String branchCode) {
        return branchProfileService.findByBranchCode(branchCode);
    }
}
