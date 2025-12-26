package com.example.demo.repository;

import com.example.demo.entity.AcademicEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicEventRepository extends JpaRepository<AcademicEvent, Long> {
    List<AcademicEvent> findByBranchId(Long branchId);
}
