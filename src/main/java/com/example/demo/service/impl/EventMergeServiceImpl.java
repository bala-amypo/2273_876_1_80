package com.example.demo.service.impl;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.entity.EventMergeRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AcademicEventRepository;
import com.example.demo.repository.EventMergeRecordRepository;
import com.example.demo.service.EventMergeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventMergeServiceImpl implements EventMergeService {

    private final EventMergeRecordRepository eventMergeRecordRepository;
    private final AcademicEventRepository academicEventRepository;

    // 🔹 Constructor injection (as per rules)
    public EventMergeServiceImpl(
            EventMergeRecordRepository eventMergeRecordRepository,
            AcademicEventRepository academicEventRepository
    ) {
        this.eventMergeRecordRepository = eventMergeRecordRepository;
        this.academicEventRepository = academicEventRepository;
    }

    // 🔹 Merge multiple events into one record
    @Override
    public EventMergeRecord mergeEvents(List<Long> eventIds, String reason) {

        // Validate event IDs exist
        List<AcademicEvent> events = eventIds.stream()
                .map(id -> academicEventRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Event not found with id: " + id)))
                .collect(Collectors.toList());

        // Determine merged start and end dates
        LocalDate mergedStartDate = events.stream()
                .map(AcademicEvent::getStartDate)
                .min(LocalDate::compareTo)
                .orElseThrow();

        LocalDate mergedEndDate = events.stream()
                .map(AcademicEvent::getEndDate)
                .max(LocalDate::compareTo)
                .orElseThrow();

        // Create comma-separated source event IDs
        String sourceEventIds = eventIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        // Create merged title (simple strategy)
        String mergedTitle = "Merged Events: " + sourceEventIds;

        // Create merge record
        EventMergeRecord mergeRecord = new EventMergeRecord(
                null,
                sourceEventIds,
                mergedTitle,
                mergedStartDate,
                mergedEndDate,
                reason,
                null
        );

        // Save merge record
        return eventMergeRecordRepository.save(mergeRecord);
    }

    // 🔹 Get all merge records
    @Override
    public List<EventMergeRecord> getAllMergeRecords() {
        return eventMergeRecordRepository.findAll();
    }

    // 🔹 Get merge record by ID
    @Override
    public EventMergeRecord getMergeRecordById(Long id) {
        return eventMergeRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Merge record not found"));
    }

    // Get merge records by date range
    @Override
    public List<EventMergeRecord> getMergeRecordsByDate(LocalDate start, LocalDate end) {
        return eventMergeRecordRepository
                .findByMergedStartDateBetween(start, end);
    }
}
