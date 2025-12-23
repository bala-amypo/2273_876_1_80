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

    /* ---------------- Constructor Injection ---------------- */

    public EventMergeServiceImpl(EventMergeRecordRepository eventMergeRecordRepository,
                                 AcademicEventRepository academicEventRepository) {
        this.eventMergeRecordRepository = eventMergeRecordRepository;
        this.academicEventRepository = academicEventRepository;
    }

    /* ---------------- Merge Events ---------------- */

    @Override
    public EventMergeRecord mergeEvents(List<Long> eventIds, String reason) {

        // Validate all event IDs exist
        List<AcademicEvent> events = eventIds.stream()
                .map(id -> academicEventRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Event not found with id: " + id)))
                .collect(Collectors.toList());

        // Merge start date = earliest start date
        LocalDate mergedStartDate = events.stream()
                .map(AcademicEvent::getStartDate)
                .min(LocalDate::compareTo)
                .orElseThrow();

        // Merge end date = latest end date
        LocalDate mergedEndDate = events.stream()
                .map(AcademicEvent::getEndDate)
                .max(LocalDate::compareTo)
                .orElseThrow();

        // Combine event titles
        String mergedTitle = events.stream()
                .map(AcademicEvent::getTitle)
                .collect(Collectors.joining(" + "));

        // Comma-separated event IDs
        String sourceEventIds = eventIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        EventMergeRecord mergeRecord = new EventMergeRecord();
        mergeRecord.setSourceEventIds(sourceEventIds);
        mergeRecord.setMergedTitle(mergedTitle);
        mergeRecord.setMergedStartDate(mergedStartDate);
        mergeRecord.setMergedEndDate(mergedEndDate);
        mergeRecord.setMergeReason(reason);

        return eventMergeRecordRepository.save(mergeRecord);
    }

    /* ---------------- Fetch All Records ---------------- */

    @Override
    public List<EventMergeRecord> getAllMergeRecords() {
        return eventMergeRecordRepository.findAll();
    }

    /* ---------------- Fetch By ID ---------------- */

    @Override
    public EventMergeRecord getMergeRecordById(Long id) {
        return eventMergeRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Merge record not found"));
    }

    /* ---------------- Fetch By Date Range ---------------- */

    @Override
    public List<EventMergeRecord> getMergeRecordsByDate(LocalDate start, LocalDate end) {
        return eventMergeRecordRepository
                .findByMergedStartDateBetween(start, end);
    }
}
