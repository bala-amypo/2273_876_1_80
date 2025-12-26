package com.example.demo.service.impl;

import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.HarmonizedCalendarRepository;
import com.example.demo.service.HarmonizedCalendarService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HarmonizedCalendarServiceImpl implements HarmonizedCalendarService {
    
    private final HarmonizedCalendarRepository harmonizedCalendarRepository;
    
    public HarmonizedCalendarServiceImpl(HarmonizedCalendarRepository harmonizedCalendarRepository) {
        this.harmonizedCalendarRepository = harmonizedCalendarRepository;
    }
    
    @Override
    public HarmonizedCalendar generateHarmonizedCalendar(String title, String generatedBy) {
        HarmonizedCalendar calendar = new HarmonizedCalendar();
        calendar.setTitle(title);
        calendar.setGeneratedBy(generatedBy);
        calendar.setGeneratedAt(LocalDateTime.now());
        calendar.setEventsJson("{}");
        
        if (calendar.getEffectiveFrom() != null && calendar.getEffectiveTo() != null) {
            if (calendar.getEffectiveFrom().isAfter(calendar.getEffectiveTo())) {
                throw new ValidationException("Effective from date must be before or equal to effective to date");
            }
        }
        
        return harmonizedCalendarRepository.save(calendar);
    }
    
    @Override
    public HarmonizedCalendar getCalendarById(Long id) {
        return harmonizedCalendarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calendar not found with id: " + id));
    }
    
    @Override
    public List<HarmonizedCalendar> getAllCalendars() {
        return harmonizedCalendarRepository.findAll();
    }
    
    @Override
    public List<HarmonizedCalendar> getCalendarsWithinRange(LocalDate start, LocalDate end) {
        return harmonizedCalendarRepository.findByEffectiveFromBetween(start, end);
    }
}