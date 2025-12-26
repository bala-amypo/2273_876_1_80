package com.example.demo.controller;

import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.service.impl.HarmonizedCalendarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/harmonized-calendars")
public class HarmonizedCalendarController {

    @Autowired
    private HarmonizedCalendarServiceImpl harmonizedCalendarService;

    @PostMapping("/generate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CALENDAR_MANAGER')")
    public ResponseEntity<HarmonizedCalendar> generate(@RequestParam String title,
                                                       @RequestParam String generatedBy) {
        return ResponseEntity.ok(harmonizedCalendarService.generateHarmonizedCalendar(title, generatedBy));
    }

    @GetMapping("/range")
    @PreAuthorize("hasRole('ADMIN') or hasRole('REVIEWER')")
    public ResponseEntity<List<HarmonizedCalendar>> getCalendarsInRange(@RequestParam String start,
                                                                        @RequestParam String end) {
        return ResponseEntity.ok(harmonizedCalendarService.getCalendarsWithinRange(
                java.time.LocalDate.parse(start),
                java.time.LocalDate.parse(end)
        ));
    }
}
