package com.example.demo.controller;

import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.service.HarmonizedCalendarService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/harmonized-calendars")
public class HarmonizedCalendarController {

    private final HarmonizedCalendarService harmonizedCalendarService;

    public HarmonizedCalendarController(HarmonizedCalendarService harmonizedCalendarService) {
        this.harmonizedCalendarService = harmonizedCalendarService;
    }

    @PostMapping("/generate")
    public HarmonizedCalendar generate(@RequestBody Map<String, String> body) {
        return harmonizedCalendarService.generateHarmonizedCalendar(
                body.get("title"),
                body.get("generatedBy")
        );
    }

    @GetMapping("/{id}")
    public HarmonizedCalendar getById(@PathVariable Long id) {
        return harmonizedCalendarService.getCalendarById(id);
    }

    @GetMapping
    public Object getAll() {
        return harmonizedCalendarService.getAllCalendars();
    }

    @GetMapping("/range")
    public Object getByRange(@RequestParam LocalDate start,
                             @RequestParam LocalDate end) {
        return harmonizedCalendarService.getCalendarsWithinRange(start, end);
    }
}
