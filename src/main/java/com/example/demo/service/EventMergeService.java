package com.example.demo.service;

import com.example.demo.entity.EventMergeRecord;

import java.util.List;

public interface EventMergeService {

    EventMergeRecord mergeEvents(List<Long> eventIds, String reason);
}
