package com.example.demo.service;

import com.example.demo.entity.ClashRecord;

import java.util.List;

public interface ClashDetectionService {

    List<ClashRecord> getUnresolvedClashes();

    ClashRecord resolveClash(Long clashId);
}
