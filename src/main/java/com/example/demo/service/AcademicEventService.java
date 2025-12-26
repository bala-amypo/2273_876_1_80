package com.example.demo.service;

import com.example.demo.entity.AcademicEvent;

import java.util.List;

public interface AcademicEventService {
    AcademicEvent createEvent(AcademicEvent event);
    AcademicEvent updateEvent(Long id, AcademicEvent e);
    AcademicEvent getEventById(Long id);
    List<AcademicEvent> getAllEvents();
    List<AcademicEvent> getEventsByBranch(Long branchId);
}
