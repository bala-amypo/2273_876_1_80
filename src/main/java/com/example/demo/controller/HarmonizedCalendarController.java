package com.example.demo.controller;


import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.service.HarmonizedCalendarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/harmonized-calendars")
public class HarmonizedCalendarController {

    private final HarmonizedCalendarService harmonizedCalendarService;

    // Constructor Injection
    public HarmonizedCalendarController(
            HarmonizedCalendarService harmonizedCalendarService
    ) {
        this.harmonizedCalendarService = harmonizedCalendarService;
    }

    // 🔐 POST /api/harmonized-calendars/generate
    @PostMapping("/generate")
    public HarmonizedCalendar generateCalendar(
            @RequestBody Map<String, String> request
    ) {
        String title = request.get("title");
        String generatedBy = request.get("generatedBy");

        return harmonizedCalendarService
                .generateHarmonizedCalendar(title, generatedBy);
    }

    // 🔐 GET /api/harmonized-calendars/{id}
    @GetMapping("/{id}")
    public HarmonizedCalendar getCalendarById(@PathVariable Long id) {
        return harmonizedCalendarService.getCalendarById(id);
    }

    // 🔐 GET /api/harmonized-calendars
    @GetMapping
    public List<HarmonizedCalendar> getAllCalendars() {
        return harmonizedCalendarService.getAllCalendars();
    }

    // 🔐 GET /api/harmonized-calendars/range
    @GetMapping("/range")
    public List<HarmonizedCalendar> getCalendarsWithinRange(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end
    ) {
        return harmonizedCalendarService
                .getCalendarsWithinRange(start, end);
    }
}
