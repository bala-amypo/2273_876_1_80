package com.example.demo.service.impl;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.AcademicEventRepository;
import com.example.demo.service.AcademicEventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicEventServiceImpl implements AcademicEventService {

    private final AcademicEventRepository repo;

    public AcademicEventServiceImpl(AcademicEventRepository repo) {
        this.repo = repo;
    }

    @Override
    public AcademicEvent createEvent(AcademicEvent e) {
        if (e.getStartDate().isAfter(e.getEndDate()))
            throw new ValidationException("startDate cannot be after endDate");
        return repo.save(e);
    }

    @Override
    public AcademicEvent updateEvent(Long id, AcademicEvent e) {
        AcademicEvent existing = getEventById(id);

        if (e.getStartDate().isAfter(e.getEndDate()))
            throw new ValidationException("startDate cannot be after endDate");

        existing.setTitle(e.getTitle());
        existing.setEventType(e.getEventType());
        existing.setStartDate(e.getStartDate());
        existing.setEndDate(e.getEndDate());
        existing.setDescription(e.getDescription());
        existing.setLocation(e.getLocation());
        return repo.save(existing);
    }

    @Override
    public AcademicEvent getEventById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    @Override
    public List<AcademicEvent> getAllEvents() {
        return repo.findAll();
    }

    @Override
    public List<AcademicEvent> getEventsByBranch(Long branchId) {
        return repo.findByBranchId(branchId);
    }
}
