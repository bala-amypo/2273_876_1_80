package com.example.demo.service.impl;

import com.example.demo.entity.ClashRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ClashRecordRepository;
import com.example.demo.service.ClashDetectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClashDetectionServiceImpl implements ClashDetectionService {

    private final ClashRecordRepository repo;

    public ClashDetectionServiceImpl(ClashRecordRepository repo) {
        this.repo = repo;
    }

    @Override
    public ClashRecord logClash(ClashRecord clash) {
        return repo.save(clash);
    }

    @Override
    public List<ClashRecord> getClashesForEvent(Long id) {
        return repo.findByEventAIdOrEventBId(id, id);
    }

    @Override
    public ClashRecord resolveClash(Long id) {
        ClashRecord cr = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clash not found"));
        cr.setResolved(true);
        return repo.save(cr);
    }

    @Override
    public List<ClashRecord> getUnresolvedClashes() {
        return repo.findByResolvedFalse();
    }

    @Override
    public List<ClashRecord> getAllClashes() {
        return repo.findAll();
    }
}
