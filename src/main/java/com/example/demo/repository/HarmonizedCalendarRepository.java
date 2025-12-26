package com.example.demo.repository;

import com.example.demo.entity.HarmonizedCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HarmonizedCalendarRepository extends JpaRepository<HarmonizedCalendar, Long> {
    List<HarmonizedCalendar> findByEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(LocalDate date1, LocalDate date2);
    List<HarmonizedCalendar> findByEffectiveFromBetween(LocalDate start, LocalDate end);
}