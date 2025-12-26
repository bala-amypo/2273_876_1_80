package com.example.demo.controller;

import com.example.demo.entity.BranchProfile;
import com.example.demo.service.BranchProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@Tag(name = "Branch Profiles")
public class BranchProfileController {

    private final BranchProfileService service;

    public BranchProfileController(BranchProfileService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a branch")
    public BranchProfile create(@RequestBody BranchProfile b) {
        return service.createBranch(b);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Activate / Deactivate branch")
    public BranchProfile update(@PathVariable Long id,
                                @RequestParam boolean active) {
        return service.updateBranchStatus(id, active);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get branch by ID")
    public BranchProfile get(@PathVariable Long id) {
        return service.getBranchById(id);
    }

    @GetMapping
    @Operation(summary = "List all branches")
    public List<BranchProfile> getAll() {
        return service.getAllBranches();
    }

    @GetMapping("/lookup/{code}")
    @Operation(summary = "Find branch by code")
    public BranchProfile find(@PathVariable String code) {
        return service.findByBranchCode(code);
    }
}
