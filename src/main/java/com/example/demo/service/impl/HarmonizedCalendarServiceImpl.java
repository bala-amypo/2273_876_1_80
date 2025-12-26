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

    private final HarmonizedCalendarRepository repo;

    public HarmonizedCalendarServiceImpl(HarmonizedCalendarRepository repo) {
        this.repo = repo;
    }

    @Override
    public HarmonizedCalendar generateHarmonizedCalendar(String title, String generatedBy) {
        HarmonizedCalendar c = new HarmonizedCalendar();
        c.setTitle(title);
        c.setGeneratedBy(generatedBy);
        c.setEventsJson("[]");
        c.setEffectiveFrom(LocalDate.now());
        c.setEffectiveTo(LocalDate.now().plusDays(30));
        return repo.save(c);
    }

    @Override
    public HarmonizedCalendar getCalendarById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calendar not found"));
    }

    @Override
    public List<HarmonizedCalendar> getAllCalendars() {
        return repo.findAll();
    }

    @Override
    public List<HarmonizedCalendar> getCalendarsWithinRange(LocalDate s, LocalDate e) {
        return repo
                .findByEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(s, e);
    }
}
