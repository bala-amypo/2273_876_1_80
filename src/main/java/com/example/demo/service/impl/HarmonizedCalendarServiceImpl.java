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

    public HarmonizedCalendarServiceImpl(HarmonizedCalendarRepository harmonizedCalendarRepository) {
        this.harmonizedCalendarRepository = harmonizedCalendarRepository;
    }

    @Override
    public HarmonizedCalendar generateHarmonizedCalendar(String title, String generatedBy) {

        HarmonizedCalendar cal = new HarmonizedCalendar();
        cal.setTitle(title);
        cal.setGeneratedBy(generatedBy);
        cal.setEffectiveFrom(LocalDate.now());
        cal.setEffectiveTo(LocalDate.now().plusDays(30));
        cal.setEventsJson("[]");

        return harmonizedCalendarRepository.save(cal);
    }

    @Override
    public HarmonizedCalendar getCalendarById(Long id) {
        return harmonizedCalendarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calendar not found"));
    }

    @Override
    public List<HarmonizedCalendar> getAllCalendars() {
        return harmonizedCalendarRepository.findAll();
    }

    @Override
    public List<HarmonizedCalendar> getCalendarsWithinRange(LocalDate start, LocalDate end) {
        return harmonizedCalendarRepository
                .findByEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(start, end);
    }
}
