package com.example.demo.service;

import com.example.demo.entity.BranchProfile;

public interface BranchProfileService {

    BranchProfile createBranch(BranchProfile branch);

    BranchProfile updateBranchStatus(Long id, boolean active);
}
