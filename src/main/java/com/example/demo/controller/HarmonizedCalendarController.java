package com.example.demo.controller;

import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.service.HarmonizedCalendarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/harmonized-calendars")
public class HarmonizedCalendarController {

    private final HarmonizedCalendarService harmonizedCalendarService;

    // Constructor Injection (NO Lombok)
    public HarmonizedCalendarController(
            HarmonizedCalendarService harmonizedCalendarService) {
        this.harmonizedCalendarService = harmonizedCalendarService;
    }

    /**
     * 1. Generate Harmonized Calendar
     * POST /api/harmonized-calendars/generate
     * Body: { "title": "", "generatedBy": "" }
     */
    @PostMapping("/generate")
    public ResponseEntity<HarmonizedCalendar> generateCalendar(
            @RequestBody Map<String, String> request) {

        String title = request.get("title");
        String generatedBy = request.get("generatedBy");

        HarmonizedCalendar calendar =
                harmonizedCalendarService.generateHarmonizedCalendar(title, generatedBy);

        return ResponseEntity.ok(calendar);
    }

    /**
     * 2. Get Calendar by ID
     * GET /api/harmonized-calendars/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<HarmonizedCalendar> getCalendarById(
            @PathVariable Long id) {

        HarmonizedCalendar calendar =
                harmonizedCalendarService.getCalendarById(id);

        return ResponseEntity.ok(calendar);
    }

    /**
     * 3. Get All Calendars
     * GET /api/harmonized-calendars
     */
    @GetMapping
    public ResponseEntity<List<HarmonizedCalendar>> getAllCalendars() {
        return ResponseEntity.ok(
                harmonizedCalendarService.getAllCalendars()
        );
    }

    /**
     * 4. Get Calendars Within Date Range
     * GET /api/harmonized-calendars/range
     * ?start=2025-01-01&end=2025-12-31
     */
    @GetMapping("/range")
    public ResponseEntity<List<HarmonizedCalendar>> getCalendarsWithinRange(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end) {

        return ResponseEntity.ok(
                harmonizedCalendarService.getCalendarsWithinRange(start, end)
        );
    }
}
