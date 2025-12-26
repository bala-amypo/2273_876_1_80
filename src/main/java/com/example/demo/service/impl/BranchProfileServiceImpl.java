package com.example.demo.service.impl;

import com.example.demo.entity.BranchProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BranchProfileRepository;
import com.example.demo.service.BranchProfileService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BranchProfileServiceImpl implements BranchProfileService {
    private final BranchProfileRepository branchProfileRepository;
    
    public BranchProfileServiceImpl(BranchProfileRepository branchProfileRepository) {
        this.branchProfileRepository = branchProfileRepository;
    }
    
    @Override
    public BranchProfile createBranch(BranchProfile branch) {
        return branchProfileRepository.save(branch);
    }
    
    @Override
    public BranchProfile updateBranchStatus(Long id, boolean active) {
        BranchProfile branch = branchProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        branch.setActive(active);
        return branchProfileRepository.save(branch);
    }
    
    @Override
    public List<BranchProfile> getAllBranches() {
        return branchProfileRepository.findAll();
    }
    
    @Override
    public BranchProfile getBranchById(Long id) {
        return branchProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }
    
    @Override
    public BranchProfile findByBranchCode(String branchCode) {
        return branchProfileRepository.findByBranchCode(branchCode)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }
}