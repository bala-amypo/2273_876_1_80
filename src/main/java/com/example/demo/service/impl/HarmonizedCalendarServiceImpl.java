package com.example.demo.service.impl;

import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.HarmonizedCalendarRepository;
import com.example.demo.service.HarmonizedCalendarService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HarmonizedCalendarServiceImpl implements HarmonizedCalendarService {

    private final HarmonizedCalendarRepository harmonizedCalendarRepository;

    // Constructor Injection (NO Lombok)
    public HarmonizedCalendarServiceImpl(
            HarmonizedCalendarRepository harmonizedCalendarRepository) {
        this.harmonizedCalendarRepository = harmonizedCalendarRepository;
    }

    @Override
    public HarmonizedCalendar generateHarmonizedCalendar(String title, String generatedBy) {

        HarmonizedCalendar calendar = new HarmonizedCalendar();
        calendar.setTitle(title);
        calendar.setGeneratedBy(generatedBy);

        // Default effective dates
        LocalDate effectiveFrom = LocalDate.now();
        LocalDate effectiveTo = effectiveFrom.plusMonths(6);

        calendar.setEffectiveFrom(effectiveFrom);
        calendar.setEffectiveTo(effectiveTo);

        // Aggregated events JSON (placeholder)
        String eventsJson = "{ \"events\": [], \"generatedOn\": \"" + LocalDate.now() + "\" }";
        calendar.setEventsJson(eventsJson);

        return harmonizedCalendarRepository.save(calendar);
    }

    @Override
    public HarmonizedCalendar getCalendarById(Long id) {
        return harmonizedCalendarRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Calendar not found"));
    }

    @Override
    public List<HarmonizedCalendar> getAllCalendars() {
        return harmonizedCalendarRepository.findAll();
    }

    @Override
    public List<HarmonizedCalendar> getCalendarsWithinRange(
            LocalDate start, LocalDate end) {

        return harmonizedCalendarRepository
                .findByEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(start, end);
    }
}
