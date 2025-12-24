package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class EventMergeRequest {

    @NotEmpty(message = "eventIds cannot be empty")
    private List<Long> eventIds;

    @NotNull(message = "reason is required")
    private String reason;

    // Getters & Setters
    public List<Long> getEventIds() {
        return eventIds;
    }

    public void setEventIds(List<Long> eventIds) {
        this.eventIds = eventIds;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
