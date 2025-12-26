package com.example.demo.service.impl;

import com.example.demo.entity.ClashRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ClashRecordRepository;
import com.example.demo.service.ClashDetectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClashDetectionServiceImpl implements ClashDetectionService {

    private final ClashRecordRepository clashRecordRepository;

    public ClashDetectionServiceImpl(ClashRecordRepository clashRecordRepository) {
        this.clashRecordRepository = clashRecordRepository;
    }

    @Override
    public ClashRecord logClash(ClashRecord clash) {
        return clashRecordRepository.save(clash);
    }

    @Override
    public List<ClashRecord> getClashesForEvent(Long eventId) {
        return clashRecordRepository.findByEventAIdOrEventBId(eventId, eventId);
    }

    @Override
    public ClashRecord resolveClash(Long clashId) {
        ClashRecord clash = clashRecordRepository.findById(clashId)
                .orElseThrow(() -> new ResourceNotFoundException("Clash not found"));
        clash.setResolved(true);
        return clashRecordRepository.save(clash);
    }

    @Override
    public List<ClashRecord> getUnresolvedClashes() {
        return clashRecordRepository.findByResolvedFalse();
    }

    @Override
    public List<ClashRecord> getAllClashes() {
        return clashRecordRepository.findAll();
    }
}
