package com.edusmart.service;

import com.edusmart.dto.AnalyticsLogDTO;
import com.edusmart.dto.PlatformOverviewDTO;

import java.util.List;

public interface AnalyticsLogService {
    AnalyticsLogDTO logEvent(Long userId,Long courseId,String eventType,String metadataJson);
    List<AnalyticsLogDTO> getUserProgressAnalytics(Long userId);
    PlatformOverviewDTO getCourseEngagement(Long courseId);
    PlatformOverviewDTO getPlatformOverview();
}
