package com.edusmart.controller;

import com.edusmart.dto.AnalyticsLogDTO;
import com.edusmart.dto.PlatformOverviewDTO;
import com.edusmart.service.AnalyticsLogService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsLogService analyticsLogService;

    @PostMapping("/log")
    public ResponseEntity<AnalyticsLogDTO> logEvent(
            @RequestParam @NotNull Long userId,
            @RequestParam @NotNull Long courseId,
            @RequestParam @NotNull String eventType,
            @RequestParam(required = false) String metadata
    ){
        AnalyticsLogDTO dto= analyticsLogService.logEvent(userId,courseId,eventType,metadata);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AnalyticsLogDTO>> getUserProgress(@PathVariable Long userId){
        List<AnalyticsLogDTO> logs=analyticsLogService.getUserProgressAnalytics(userId);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<PlatformOverviewDTO> getCourseEngagement(@PathVariable Long courseId){
        PlatformOverviewDTO dto= analyticsLogService.getCourseEngagement(courseId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/platform")
    public ResponseEntity<PlatformOverviewDTO> getPlatformOverview(){
        PlatformOverviewDTO dto=analyticsLogService.getPlatformOverview();
        return ResponseEntity.ok(dto);
    }
}
