package com.example.demo.controller;

import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.service.HarmonizedCalendarService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/harmonized-calendars")
public class HarmonizedCalendarController {

    private final HarmonizedCalendarService harmonizedCalendarService;

    public HarmonizedCalendarController(
            HarmonizedCalendarService harmonizedCalendarService) {
        this.harmonizedCalendarService = harmonizedCalendarService;
    }

    // ✅ GENERATE CALENDAR (NO DTO, using RequestParam)
    @PostMapping("/generate")
    public ResponseEntity<HarmonizedCalendar> generateCalendar(
            @RequestParam String title,
            @RequestParam String generatedBy) {

        HarmonizedCalendar calendar =
                harmonizedCalendarService.generateHarmonizedCalendar(
                        title, generatedBy);

        return ResponseEntity.ok(calendar);
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<HarmonizedCalendar> getCalendarById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                harmonizedCalendarService.getCalendarById(id));
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<List<HarmonizedCalendar>> getAllCalendars() {
        return ResponseEntity.ok(
                harmonizedCalendarService.getAllCalendars());
    }

    // ✅ GET BY DATE RANGE
    @GetMapping("/range")
    public ResponseEntity<List<HarmonizedCalendar>> getCalendarsInRange(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return ResponseEntity.ok(
                harmonizedCalendarService.getCalendarsWithinRange(start, end));
    }
}
