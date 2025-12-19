package com.example.demo.service.impl;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.repository.AcademicEventRepository;
import com.example.demo.service.AcademicEventService;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicEventServiceImpl implements AcademicEventService {

    private final AcademicEventRepository academicEventRepository;

    // ✅ Constructor injection (as required)
    public AcademicEventServiceImpl(AcademicEventRepository academicEventRepository) {
        this.academicEventRepository = academicEventRepository;
    }

    // 1️⃣ createEvent
    @Override
    public AcademicEvent createEvent(AcademicEvent event) {

        // Validate dates
        if (event.getStartDate().isAfter(event.getEndDate())) {
            throw new ValidationException("startDate cannot be after endDate");
        }

        return academicEventRepository.save(event);
    }

    // 2️⃣ getEventsByBranch
    @Override
    public List<AcademicEvent> getEventsByBranch(Long branchId) {
        return academicEventRepository.findByBranchId(branchId);
    }

    // 3️⃣ updateEvent
    @Override
    public AcademicEvent updateEvent(Long id, AcademicEvent event) {

        AcademicEvent existingEvent = academicEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        // Validate dates
        if (event.getStartDate().isAfter(event.getEndDate())) {
            throw new ValidationException("startDate cannot be after endDate");
        }

        // Update fields
        existingEvent.setTitle(event.getTitle());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setStartDate(event.getStartDate());
        existingEvent.setEndDate(event.getEndDate());
        existingEvent.setBranchId(event.getBranchId());

        return academicEventRepository.save(existingEvent);
    }

    // 4️⃣ getEventById
    @Override
    public AcademicEvent getEventById(Long id) {
        return academicEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    // 5️⃣ getAllEvents
    @Override
    public List<AcademicEvent> getAllEvents() {
        return academicEventRepository.findAll();
    }
}
