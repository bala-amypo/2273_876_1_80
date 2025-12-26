package com.example.demo.controller;

import com.example.demo.entity.ClashRecord;
import com.example.demo.service.ClashDetectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clashes")
@Tag(name = "Clash Records")
public class ClashRecordController {

    private final ClashDetectionService service;

    public ClashRecordController(ClashDetectionService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Log a clash")
    public ClashRecord log(@RequestBody ClashRecord c) {
        return service.logClash(c);
    }

    @PutMapping("/{id}/resolve")
    @Operation(summary = "Resolve clash")
    public ClashRecord resolve(@PathVariable Long id) {
        return service.resolveClash(id);
    }

    @GetMapping
    @Operation(summary = "List all clashes")
    public List<ClashRecord> all() {
        return service.getAllClashes();
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get clashes for event")
    public List<ClashRecord> byEvent(@PathVariable Long eventId) {
        return service.getClashesForEvent(eventId);
    }

    @GetMapping("/unresolved")
    @Operation(summary = "Get unresolved clashes")
    public List<ClashRecord> unresolved() {
        return service.getUnresolvedClashes();
    }
}
