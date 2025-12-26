package com.example.demo.controller;

import com.example.demo.entity.ClashRecord;
import com.example.demo.service.impl.ClashDetectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clashes")
public class ClashRecordController {

    @Autowired
    private ClashDetectionServiceImpl clashDetectionService;

    @GetMapping("/event/{eventId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('REVIEWER')")
    public ResponseEntity<List<ClashRecord>> getClashesForEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(clashDetectionService.getClashesForEvent(eventId));
    }

    @GetMapping("/unresolved")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClashRecord>> getUnresolvedClashes() {
        return ResponseEntity.ok(clashDetectionService.getUnresolvedClashes());
    }

    @PutMapping("/{id}/resolve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClashRecord> resolveClash(@PathVariable Long id) {
        return ResponseEntity.ok(clashDetectionService.resolveClash(id));
    }
}
