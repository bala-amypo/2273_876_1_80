package com.example.demo.repository;

import com.example.demo.entity.BranchProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchProfileRepository extends JpaRepository<BranchProfile, Long> {
}
