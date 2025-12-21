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

    public EventMergeRecordServiceImpl(
            EventMergeRecordRepository eventMergeRecordRepository,
            AcademicEventRepository academicEventRepository
    ) {
        this.eventMergeRecordRepository = eventMergeRecordRepository;
        this.academicEventRepository = academicEventRepository;
    }

    @Override
    public EventMergeRecord mergeEvents(List<Long> eventIds, String reason) {

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

        EventMergeRecord record = new EventMergeRecord();
        record.setSourceEventIds(sourceEventIds);
        record.setMergedTitle("Merged Events: " + sourceEventIds);
        record.setMergedStartDate(mergedStartDate);
        record.setMergedEndDate(mergedEndDate);
        record.setMergeReason(reason);

        return eventMergeRecordRepository.save(record);
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
