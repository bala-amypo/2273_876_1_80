package com.example.demo.controller;

import com.example.demo.entity.ClashRecord;
import com.example.demo.service.ClashDetectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clashes")
@Tag(name = "Clash Records")
public class ClashRecordController {
    
    private final ClashDetectionService clashDetectionService;
    
    public ClashRecordController(ClashDetectionService clashDetectionService) {
        this.clashDetectionService = clashDetectionService;
    }
    
    @PostMapping
    @Operation(summary = "Log clash record")
    public ResponseEntity<ClashRecord> logClash(@RequestBody ClashRecord clashRecord) {
        return ResponseEntity.ok(clashDetectionService.logClash(clashRecord));
    }
    
    @PutMapping("/{id}/resolve")
    @Operation(summary = "Resolve clash")
    public ResponseEntity<ClashRecord> resolveClash(@PathVariable Long id) {
        return ResponseEntity.ok(clashDetectionService.resolveClash(id));
    }
    
    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get clashes for event")
    public ResponseEntity<List<ClashRecord>> getClashesForEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(clashDetectionService.getClashesForEvent(eventId));
    }
    
    @GetMapping("/unresolved")
    @Operation(summary = "Get unresolved clashes")
    public ResponseEntity<List<ClashRecord>> getUnresolvedClashes() {
        return ResponseEntity.ok(clashDetectionService.getUnresolvedClashes());
    }
    
    @GetMapping
    @Operation(summary = "Get all clashes")
    public ResponseEntity<List<ClashRecord>> getAllClashes() {
        return ResponseEntity.ok(clashDetectionService.getAllClashes());
    }
}

