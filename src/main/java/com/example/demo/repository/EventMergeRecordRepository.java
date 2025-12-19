package com.example.demo.repository;

import com.example.demo.entity.EventMergeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EventMergeRecordRepository extends JpaRepository<EventMergeRecord, Long>{
}