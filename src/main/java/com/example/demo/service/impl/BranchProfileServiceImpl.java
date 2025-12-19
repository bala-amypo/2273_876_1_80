package com.example.demo.service.implement;

import com.example.demo.entity.BranchProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BranchProfileRepository;
import com.example.demo.service.BranchProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchProfileServiceImpl implements BranchProfileService {

    private final BranchProfileRepository branchProfileRepository;

    // 🔹 Constructor Injection (RULE FOLLOWED)
    public BranchProfileServiceImpl(BranchProfileRepository branchProfileRepository) {
        this.branchProfileRepository = branchProfileRepository;
    }

    // 🔹 createBranch
    @Override
    public BranchProfile createBranch(BranchProfile branch) {
        return branchProfileRepository.save(branch);
    }

    // 🔹 updateBranchStatus
    @Override
    public BranchProfile updateBranchStatus(Long id, boolean active) {
        BranchProfile branch = branchProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        branch.setActive(active);
        return branchProfileRepository.save(branch);
    }

    // 🔹 getAllBranches
    @Override
    public List<BranchProfile> getAllBranches() {
        return branchProfileRepository.findAll();
    }

    // 🔹 getBranchById
    @Override
    public BranchProfile getBranchById(Long id) {
        return branchProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }

    // 🔹 findByBranchCode
    @Override
    public BranchProfile findByBranchCode(String branchCode) {
        return branchProfileRepository.findByBranchCode(branchCode)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }
}
