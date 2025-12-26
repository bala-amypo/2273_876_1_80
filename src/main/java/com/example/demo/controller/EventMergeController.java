package com.example.demo.controller;

import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.EventMergeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/merge-records")
public class EventMergeController {

    private final EventMergeService eventMergeService;

    public EventMergeController(EventMergeService eventMergeService) {
        this.eventMergeService = eventMergeService;
    }

    @PostMapping
    public EventMergeRecord merge(@RequestBody Map<String, Object> body) {

        @SuppressWarnings("unchecked")
        List<Integer> ids = (List<Integer>) body.get("eventIds");
        String reason = (String) body.get("reason");

        List<Long> eventIds = ids.stream().map(Long::valueOf).toList();

        return eventMergeService.mergeEvents(eventIds, reason);
    }

    @GetMapping("/{id}")
    public EventMergeRecord getById(@PathVariable Long id) {
        return eventMergeService.getMergeRecordById(id);
    }

    @GetMapping
    public Object getAll() {
        return eventMergeService.getAllMergeRecords();
    }

    @GetMapping("/range")
    public Object getByRange(@RequestParam LocalDate start,
                             @RequestParam LocalDate end) {
        return eventMergeService.getMergeRecordsByDate(start, end);
    }
}
