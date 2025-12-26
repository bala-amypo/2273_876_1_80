package com.example.demo.service.impl;

import com.example.demo.entity.ClashRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ClashRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClashDetectionServiceImpl {

    @Autowired
    private ClashRecordRepository clashRecordRepository;

    public List<ClashRecord> getClashesForEvent(Long eventId) {
        return clashRecordRepository.findByEventAIdOrEventBId(eventId, eventId);
    }

    public List<ClashRecord> getUnresolvedClashes() {
        return clashRecordRepository.findByResolvedFalse();
    }

    public ClashRecord resolveClash(Long id) {
        ClashRecord cr = clashRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clash not found"));
        cr.setResolved(true);
        return clashRecordRepository.save(cr);
    }
}
