package com.example.demo.service.implement;

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

    // ✅ Constructor injection (as per rules)
    public HarmonizedCalendarServiceImpl(
            HarmonizedCalendarRepository harmonizedCalendarRepository
    ) {
        this.harmonizedCalendarRepository = harmonizedCalendarRepository;
    }

    @Override
    public HarmonizedCalendar generateHarmonizedCalendar(String title, String generatedBy) {

        HarmonizedCalendar calendar = new HarmonizedCalendar();
        calendar.setTitle(title);
        calendar.setGeneratedBy(generatedBy);

        // 🔹 Defaults (replace later with event-derived values if needed)
        calendar.setEffectiveFrom(LocalDate.now());
        calendar.setEffectiveTo(LocalDate.now().plusMonths(1));

        // 🔹 Aggregated events JSON (placeholder)
        calendar.setEventsJson("[]");

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
    public List<HarmonizedCalendar> getCalendarsWithinRange(LocalDate start, LocalDate end) {
        return harmonizedCalendarRepository
                .findByEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(start, end);
    }
}
