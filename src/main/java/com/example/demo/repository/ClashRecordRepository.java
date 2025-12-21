package com.example.demo.repository;

import com.example.demo.entity.ClashRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClashRecordRepository
        extends JpaRepository<ClashRecord, Long> {

    // 🔹 Find clashes where the event participates as A or B
    List<ClashRecord> findByEventAIdOrEventBId(
            Long eventAId,
            Long eventBId
    );

    // 🔹 Find all unresolved clashes
    List<ClashRecord> findByResolvedFalse();
}