package com.example.demo.controller;

import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.EventMergeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/merge-records")
@Tag(name = "Event Merge Records")
public class EventMergeController {

    private final EventMergeService service;

    public EventMergeController(EventMergeService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Merge multiple events")
    public EventMergeRecord merge(@RequestBody Map<String, Object> body) {
        List<Integer> ids = (List<Integer>) body.get("eventIds");
        String reason = body.get("reason").toString();

        List<Long> longs = ids.stream().map(Integer::longValue).toList();
        return service.mergeEvents(longs, reason);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get merge record by ID")
    public EventMergeRecord get(@PathVariable Long id) {
        return service.getMergeRecordById(id);
    }

    @GetMapping
    @Operation(summary = "List all merge records")
    public List<EventMergeRecord> all() {
        return service.getAllMergeRecords();
    }

    @GetMapping("/range")
    @Operation(summary = "Get merge records between dates")
    public List<EventMergeRecord> range(@RequestParam LocalDate start,
                                        @RequestParam LocalDate end) {
        return service.getMergeRecordsByDate(start, end);
    }
}
