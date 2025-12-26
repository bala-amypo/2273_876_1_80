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

    private final EventMergeRecordRepository repo;
    private final AcademicEventRepository eventRepo;

    public EventMergeServiceImpl(EventMergeRecordRepository repo,
                                 AcademicEventRepository eventRepo) {
        this.repo = repo;
        this.eventRepo = eventRepo;
    }

    @Override
    public EventMergeRecord mergeEvents(List<Long> ids, String reason) {

        List<AcademicEvent> events = eventRepo.findAllById(ids);

        if (events.isEmpty())
            throw new ResourceNotFoundException("No events found");

        String joinedIds = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        LocalDate start = events.stream()
                .map(AcademicEvent::getStartDate)
                .min(LocalDate::compareTo).get();

        LocalDate end = events.stream()
                .map(AcademicEvent::getEndDate)
                .max(LocalDate::compareTo).get();

        EventMergeRecord mr = new EventMergeRecord();
        mr.setSourceEventIds(joinedIds);
        mr.setMergedTitle("Merged Event");
        mr.setMergedStartDate(start);
        mr.setMergedEndDate(end);
        mr.setMergeReason(reason);

        return repo.save(mr);
    }

    @Override
    public List<EventMergeRecord> getAllMergeRecords() {
        return repo.findAll();
    }

    @Override
    public EventMergeRecord getMergeRecordById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merge record not found"));
    }

    @Override
    public List<EventMergeRecord> getMergeRecordsByDate(LocalDate start, LocalDate end) {
        return repo.findByMergedStartDateBetween(start, end);
    }
}
