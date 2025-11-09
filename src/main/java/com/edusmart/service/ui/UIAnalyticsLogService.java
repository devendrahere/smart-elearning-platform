package com.edusmart.service.ui;

import com.edusmart.dto.AnalyticsLogDTO;
import com.edusmart.dto.PlatformOverviewDTO;
import com.edusmart.service.AnalyticsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UIAnalyticsLogService {

    @Autowired
    private AnalyticsLogService analyticsLogService;

    public List<AnalyticsLogDTO> getUserProgressAnalytics(Long userId){
        return analyticsLogService.getUserProgressAnalytics(userId);
    }

    public PlatformOverviewDTO getPlatformOverview(){
        return analyticsLogService.getPlatformOverview();
    }
}
