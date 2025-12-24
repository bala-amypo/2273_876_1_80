package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.EventMergeService;

@RestController
@RequestMapping("/api/merge-records")
public class EventMergeController {

    private final EventMergeService eventMergeService;

    public EventMergeController(EventMergeService eventMergeService) {
        this.eventMergeService = eventMergeService;
    }

    /* --------------------------------------------------------
       1. POST /api/merge-records
    --------------------------------------------------------- */

    @PostMapping
    public ResponseEntity<ApiResponse> mergeEvents(
            @RequestBody Map<String, Object> request) {

        /* ---- Validation ---- */

        if (!request.containsKey("eventIds") || request.get("eventIds") == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "eventIds is required", null));
        }

        if (!request.containsKey("reason") || request.get("reason") == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "reason is required", null));
        }

        @SuppressWarnings("unchecked")
        List<Integer> eventIdsInt;
        try {
            eventIdsInt = (List<Integer>) request.get("eventIds");
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "eventIds must be an array of numbers", null));
        }

        if (eventIdsInt.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "eventIds cannot be empty", null));
        }

        String reason = request.get("reason").toString().trim();
        if (reason.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "reason cannot be blank", null));
        }

        /* ---- Convert Integer → Long ---- */

        List<Long> eventIds = eventIdsInt.stream()
                .map(Integer::longValue)
                .toList();

        /* ---- Service call ---- */

        EventMergeRecord record =
                eventMergeService.mergeEvents(eventIds, reason);

        return ResponseEntity.ok(
                new ApiResponse(true, "Events merged successfully", record)
        );
    }

    /* --------------------------------------------------------
       2. GET /api/merge-records/{id}
    --------------------------------------------------------- */

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getMergeRecordById(
            @PathVariable Long id) {

        EventMergeRecord record =
                eventMergeService.getMergeRecordById(id);

        return ResponseEntity.ok(
                new ApiResponse(true, "Merge record fetched", record)
        );
    }

    /* --------------------------------------------------------
       3. GET /api/merge-records
    --------------------------------------------------------- */

    @GetMapping
    public ResponseEntity<ApiResponse> getAllMergeRecords() {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "All merge records fetched",
                        eventMergeService.getAllMergeRecords()
                )
        );
    }

    /* --------------------------------------------------------
       4. GET /api/merge-records/range
    --------------------------------------------------------- */

    @GetMapping("/range")
    public ResponseEntity<ApiResponse> getMergeRecordsByDateRange(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,

            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Merge records fetched by date range",
                        eventMergeService.getMergeRecordsByDate(start, end)
                )
        );
    }
}
