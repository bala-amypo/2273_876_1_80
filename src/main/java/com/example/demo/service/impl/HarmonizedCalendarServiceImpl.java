package com.example.demo.service.impl;

import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.HarmonizedCalendarRepository;
import com.example.demo.service.HarmonizedCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HarmonizedCalendarServiceImpl implements HarmonizedCalendarService {

    private final HarmonizedCalendarRepository harmonizedCalendarRepository;

    /**
     * Generate Harmonized Calendar
     */
    @Override
    public HarmonizedCalendar generateHarmonizedCalendar(String title, String generatedBy) {

        HarmonizedCalendar calendar = new HarmonizedCalendar();
        calendar.setTitle(title);
        calendar.setGeneratedBy(generatedBy);

        // Default effective range (can be derived from events later)
        LocalDate effectiveFrom = LocalDate.now();
        LocalDate effectiveTo = effectiveFrom.plusMonths(6);

        calendar.setEffectiveFrom(effectiveFrom);
        calendar.setEffectiveTo(effectiveTo);

        // Aggregated events JSON (placeholder – can be enhanced)
        String eventsJson = """
                {
                  "events": [],
                  "generatedOn": "%s"
                }
                """.formatted(LocalDate.now());

        calendar.setEventsJson(eventsJson);

        return harmonizedCalendarRepository.save(calendar);
    }

    /**
     * Get Calendar by ID
     */
    @Override
    public HarmonizedCalendar getCalendarById(Long id) {
        return harmonizedCalendarRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Calendar not found"));
    }

    /**
     * Get All Calendars
     */
    @Override
    public List<HarmonizedCalendar> getAllCalendars() {
        return harmonizedCalendarRepository.findAll();
    }

    /**
     * Get Calendars Within Date Range
     */
    @Override
    public List<HarmonizedCalendar> getCalendarsWithinRange(LocalDate start, LocalDate end) {
        return harmonizedCalendarRepository
                .findByEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(start, end);
    }
}
