package com.example.demo.controller;

import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.service.HarmonizedCalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/harmonized-calendars")
@Tag(name = "Harmonized Calendars")
public class HarmonizedCalendarController {

    private final HarmonizedCalendarService service;

    public HarmonizedCalendarController(HarmonizedCalendarService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate harmonized calendar")
    public HarmonizedCalendar generate(@RequestBody Map<String, String> req) {
        return service.generateHarmonizedCalendar(
                req.get("title"),
                req.get("generatedBy")
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get calendar by ID")
    public HarmonizedCalendar get(@PathVariable Long id) {
        return service.getCalendarById(id);
    }

    @GetMapping
    @Operation(summary = "List all calendars")
    public List<HarmonizedCalendar> all() {
        return service.getAllCalendars();
    }

    @GetMapping("/range")
    @Operation(summary = "Calendars within range")
    public List<HarmonizedCalendar> range(@RequestParam LocalDate start,
                                          @RequestParam LocalDate end) {
        return service.getCalendarsWithinRange(start, end);
    }
}
