package com.example.demo.service.impl;

import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.repository.HarmonizedCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HarmonizedCalendarServiceImpl {

    @Autowired
    private HarmonizedCalendarRepository harmonizedCalendarRepository;

    public HarmonizedCalendar generateHarmonizedCalendar(String title, String generatedBy) {
        HarmonizedCalendar cal = new HarmonizedCalendar();
        cal.setTitle(title);
        cal.setGeneratedBy(generatedBy);
        cal.setEffectiveFrom(LocalDate.now());
        cal.setEffectiveTo(LocalDate.now().plusMonths(3));
        cal.prePersist();
        return harmonizedCalendarRepository.save(cal);
    }

    public List<HarmonizedCalendar> getCalendarsWithinRange(LocalDate start, LocalDate end) {
        return harmonizedCalendarRepository.findByEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(start, end);
    }
}
