package com.example.demo.service.impl;

import com.example.demo.entity.BranchProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BranchProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchProfileServiceImpl {

    @Autowired
    private BranchProfileRepository branchProfileRepository;

    public BranchProfile createBranch(BranchProfile branch) {
        branch.prePersist();
        return branchProfileRepository.save(branch);
    }

    public BranchProfile updateBranchStatus(Long id, boolean active) {
        BranchProfile branch = branchProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        branch.setActive(active);
        return branchProfileRepository.save(branch);
    }

    public List<BranchProfile> getAllBranches() {
        return branchProfileRepository.findAll();
    }
}
