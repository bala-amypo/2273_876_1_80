package com.example.demo.controller;

import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.service.HarmonizedCalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/harmonized-calendars")
@Tag(name = "Harmonized Calendars")
public class HarmonizedCalendarController {
    
    private final HarmonizedCalendarService harmonizedCalendarService;
    
    public HarmonizedCalendarController(HarmonizedCalendarService harmonizedCalendarService) {
        this.harmonizedCalendarService = harmonizedCalendarService;
    }
    
    @PostMapping("/generate")
    @Operation(summary = "Generate harmonized calendar")
    public ResponseEntity<HarmonizedCalendar> generateHarmonizedCalendar(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        String generatedBy = request.get("generatedBy");
        return ResponseEntity.ok(harmonizedCalendarService.generateHarmonizedCalendar(title, generatedBy));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get calendar by ID")
    public ResponseEntity<HarmonizedCalendar> getCalendarById(@PathVariable Long id) {
        return ResponseEntity.ok(harmonizedCalendarService.getCalendarById(id));
    }
    
    @GetMapping
    @Operation(summary = "Get all calendars")
    public ResponseEntity<List<HarmonizedCalendar>> getAllCalendars() {
        return ResponseEntity.ok(harmonizedCalendarService.getAllCalendars());
    }
    
    @GetMapping("/range")
    @Operation(summary = "Get calendars within date range")
    public ResponseEntity<List<HarmonizedCalendar>> getCalendarsWithinRange(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(harmonizedCalendarService.getCalendarsWithinRange(start, end));
    }
}