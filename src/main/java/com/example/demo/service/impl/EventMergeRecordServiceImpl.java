package com.example.demo.service.impl;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.entity.EventMergeRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AcademicEventRepository;
import com.example.demo.repository.EventMergeRecordRepository;
import com.example.demo.service.EventMergeRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventMergeRecordServiceImpl implements EventMergeRecordService {

    private final EventMergeRecordRepository eventMergeRecordRepository;
    private final AcademicEventRepository academicEventRepository;

    // Constructor injection
    public EventMergeRecordServiceImpl(
            EventMergeRecordRepository eventMergeRecordRepository,
            AcademicEventRepository academicEventRepository
    ) {
        this.eventMergeRecordRepository = eventMergeRecordRepository;
        this.academicEventRepository = academicEventRepository;
    }

    @Override
    public EventMergeRecord mergeEvents(List<Long> eventIds, String reason) {

        // Validate all event IDs
        List<AcademicEvent> events = eventIds.stream()
                .map(id -> academicEventRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Event not found with id: " + id)))
                .collect(Collectors.toList());

        LocalDate mergedStartDate = events.stream()
                .map(AcademicEvent::getStartDate)
                .min(LocalDate::compareTo)
                .orElseThrow();

        LocalDate mergedEndDate = events.stream()
                .map(AcademicEvent::getEndDate)
                .max(LocalDate::compareTo)
                .orElseThrow();

        String sourceEventIds = eventIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String mergedTitle = "Merged Events: " + sourceEventIds;

        // ✅ Use NO-ARG constructor (fixes compile error)
        EventMergeRecord mergeRecord = new EventMergeRecord();
        mergeRecord.setSourceEventIds(sourceEventIds);
        mergeRecord.setMergedTitle(mergedTitle);
        mergeRecord.setMergedStartDate(mergedStartDate);
        mergeRecord.setMergedEndDate(mergedEndDate);
        mergeRecord.setMergeReason(reason);

        return eventMergeRecordRepository.save(mergeRecord);
    }

    @Override
    public List<EventMergeRecord> getAllMergeRecords() {
        return eventMergeRecordRepository.findAll();
    }

    @Override
    public EventMergeRecord getMergeRecordById(Long id) {
        return eventMergeRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Merge record not found"));
    }

    @Override
    public List<EventMergeRecord> getMergeRecordsByDate(LocalDate start, LocalDate end) {
        return eventMergeRecordRepository
                .findByMergedStartDateBetween(start, end);
    }
}
