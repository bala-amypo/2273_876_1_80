package com.example.demo.controller;


import com.example.demo.entity.ClashRecord;
import com.example.demo.service.ClashDetectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clashes")
public class ClashRecordController {

    private final ClashDetectionService clashDetectionService;

    // Constructor Injection
    public ClashRecordController(ClashDetectionService clashDetectionService) {
        this.clashDetectionService = clashDetectionService;
    }

    // 🔐 POST /api/clashes
    @PostMapping
    public ClashRecord logClash(@RequestBody ClashRecord clash) {
        return clashDetectionService.logClash(clash);
    }

    // 🔐 PUT /api/clashes/{id}/resolve
    @PutMapping("/{id}/resolve")
    public ClashRecord resolveClash(@PathVariable Long id) {
        return clashDetectionService.resolveClash(id);
    }

    // 🔐 GET /api/clashes/event/{eventId}
    @GetMapping("/event/{eventId}")
    public List<ClashRecord> getClashesForEvent(@PathVariable Long eventId) {
        return clashDetectionService.getClashesForEvent(eventId);
    }

    // 🔐 GET /api/clashes/unresolved
    @GetMapping("/unresolved")
    public List<ClashRecord> getUnresolvedClashes() {
        return clashDetectionService.getUnresolvedClashes();
    }

    // 🔐 GET /api/clashes
    @GetMapping
    public List<ClashRecord> getAllClashes() {
        return clashDetectionService.getAllClashes();
    }
}
