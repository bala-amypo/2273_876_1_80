package com.example.demo.service.implement;

import com.example.demo.entity.BranchProfile;
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
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));

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
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    @Override
    public BranchProfile findByBranchCode(String branchCode) {
        return branchProfileRepository.findByBranchCode(branchCode)
                .orElseThrow(() -> new RuntimeException("Branch not found with code: " + branchCode));
    }
}
