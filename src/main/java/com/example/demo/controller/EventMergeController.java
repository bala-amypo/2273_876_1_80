package com.example.demo.controller;

import com.example.demo.dto.MergeEventsRequest;
import com.example.demo.service.EventMergeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventMergeController {

    private final EventMergeService eventMergeService;

    public EventMergeController(EventMergeService eventMergeService) {
        this.eventMergeService = eventMergeService;
    }

    @PostMapping("/merge")
    public ResponseEntity<?> merge(@RequestBody MergeEventsRequest request) {
        return ResponseEntity.ok(
                eventMergeService.mergeEvents(
                        request.getEventIds(),
                        request.getReason()
                )
        );
    }
}
