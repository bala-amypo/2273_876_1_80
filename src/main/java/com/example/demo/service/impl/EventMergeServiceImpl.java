package com.example.demo.service.impl;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.entity.EventMergeRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AcademicEventRepository;
import com.example.demo.repository.EventMergeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventMergeServiceImpl {

    @Autowired
    private AcademicEventRepository academicEventRepository;

    @Autowired
    private EventMergeRecordRepository eventMergeRecordRepository;

    public EventMergeRecord mergeEvents(List<Long> eventIds, String reason) {
        List<AcademicEvent> events = academicEventRepository.findAllById(eventIds);
        if (events.isEmpty()) throw new ResourceNotFoundException("No events found to merge");

        String mergedTitle = events.stream().map(AcademicEvent::getTitle).collect(Collectors.joining(" + "));
        LocalDate start = events.stream().map(AcademicEvent::getStartDate).min(LocalDate::compareTo).get();
        LocalDate end = events.stream().map(AcademicEvent::getEndDate).max(LocalDate::compareTo).get();

        EventMergeRecord mr = new EventMergeRecord();
        mr.setSourceEventIds(events.stream().map(e -> e.getId().toString()).collect(Collectors.joining(",")));
        mr.setMergedTitle(mergedTitle);
        mr.setMergedStartDate(start);
        mr.setMergedEndDate(end);
        mr.setMergeReason(reason);
        mr.prePersist();
        return eventMergeRecordRepository.save(mr);
    }

    public List<EventMergeRecord> getMergeRecordsByDate(LocalDate start, LocalDate end) {
        return eventMergeRecordRepository.findByMergedStartDateBetween(start, end);
    }
}
