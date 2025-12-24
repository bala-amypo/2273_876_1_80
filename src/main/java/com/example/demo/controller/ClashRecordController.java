package com.example.demo.controller;

import com.example.demo.service.ClashDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clashes")
public class ClashController {

    private final ClashDetectionService clashDetectionService;

    public ClashController(ClashDetectionService clashDetectionService) {
        this.clashDetectionService = clashDetectionService;
    }

    @GetMapping("/unresolved")
    public ResponseEntity<?> getUnresolved() {
        return ResponseEntity.ok(
                clashDetectionService.getUnresolvedClashes()
        );
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<?> resolve(@PathVariable Long id) {
        return ResponseEntity.ok(
                clashDetectionService.resolveClash(id)
        );
    }
}
