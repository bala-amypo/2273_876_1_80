package com.example.demo.repository;

import com.example.demo.entity.ClashRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClashRecordRepository extends JpaRepository<ClashRecord, Long> {
    List<ClashRecord> findByEventAIdOrEventBId(Long eventAId, Long eventBId);
    List<ClashRecord> findByEventId(Long eventId);
    List<ClashRecord> findByResolvedFalse();
}