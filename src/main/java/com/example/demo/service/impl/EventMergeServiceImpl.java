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

    public EventMergeServiceImpl(EventMergeRecordRepository eventMergeRecordRepository,
                                 AcademicEventRepository academicEventRepository) {
        this.eventMergeRecordRepository = eventMergeRecordRepository;
        this.academicEventRepository = academicEventRepository;
    }

    @Override
    public EventMergeRecord mergeEvents(List<Long> eventIds, String reason) {

        List<AcademicEvent> events = academicEventRepository.findAllById(eventIds);
        if (events.isEmpty()) {
            throw new ResourceNotFoundException("No events found");
        }

        String ids = events.stream()
                .map(e -> String.valueOf(e.getId()))
                .collect(Collectors.joining(","));

        LocalDate start = events.stream()
                .map(AcademicEvent::getStartDate)
                .min(LocalDate::compareTo).orElse(null);

        LocalDate end = events.stream()
                .map(AcademicEvent::getEndDate)
                .max(LocalDate::compareTo).orElse(null);

        EventMergeRecord record = new EventMergeRecord();
        record.setSourceEventIds(ids);
        record.setMergedTitle("Merged Events");
        record.setMergedStartDate(start);
        record.setMergedEndDate(end);
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
                .orElseThrow(() -> new ResourceNotFoundException("Merge record not found"));
    }

    @Override
    public List<EventMergeRecord> getMergeRecordsByDate(LocalDate start, LocalDate end) {
        return eventMergeRecordRepository.findByMergedStartDateBetween(start, end);
    }
}
