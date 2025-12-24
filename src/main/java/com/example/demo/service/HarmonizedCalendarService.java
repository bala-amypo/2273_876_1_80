package com.example.demo.service;

import com.example.demo.entity.HarmonizedCalendar;

public interface HarmonizedCalendarService {

    HarmonizedCalendar generateHarmonizedCalendar(String title, String generatedBy);

    HarmonizedCalendar getById(Long id);
}
