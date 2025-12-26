package com.example.demo.service.impl;

import com.example.demo.entity.BranchProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BranchProfileRepository;
import com.example.demo.service.BranchProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchProfileServiceImpl implements BranchProfileService {

    private final BranchProfileRepository repo;

    public BranchProfileServiceImpl(BranchProfileRepository repo) {
        this.repo = repo;
    }

    @Override
    public BranchProfile createBranch(BranchProfile branch) {
        return repo.save(branch);
    }

    @Override
    public BranchProfile updateBranchStatus(Long id, boolean active) {
        BranchProfile bp = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        bp.setActive(active);
        return repo.save(bp);
    }

    @Override
    public List<BranchProfile> getAllBranches() {
        return repo.findAll();
    }

    @Override
    public BranchProfile getBranchById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }

    @Override
    public BranchProfile findByBranchCode(String code) {
        return repo.findByBranchCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }
}
