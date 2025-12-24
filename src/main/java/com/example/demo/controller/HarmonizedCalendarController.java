package com.example.demo.controller;

import com.example.demo.service.HarmonizedCalendarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/harmonized-calendars")
public class HarmonizedCalendarController {

    private final HarmonizedCalendarService harmonizedCalendarService;

    public HarmonizedCalendarController(HarmonizedCalendarService harmonizedCalendarService) {
        this.harmonizedCalendarService = harmonizedCalendarService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generate(@RequestParam String title,
                                      @RequestParam String generatedBy) {
        return ResponseEntity.ok(
                harmonizedCalendarService
                        .generateHarmonizedCalendar(title, generatedBy)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                harmonizedCalendarService.getById(id)
        );
    }
}
