package com.edusmart.service.implemeted;

import com.edusmart.dto.AnalyticsLogDTO;
import com.edusmart.dto.PlatformOverviewDTO;
import com.edusmart.entity.AnalyticsLog;
import com.edusmart.entity.Courses;
import com.edusmart.entity.Users;
import com.edusmart.exception.ResourcesNotFound;
import com.edusmart.repository.AnalyticsLogRepository;
import com.edusmart.repository.CourseRepository;
import com.edusmart.repository.UserRepository;
import com.edusmart.service.AnalyticsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsLogServiceImple implements AnalyticsLogService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AnalyticsLogRepository analyticsLogRepository;

    @Override
    public AnalyticsLogDTO logEvent(Long userId, Long courseId, String eventType, String metadataJson) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourcesNotFound("User not found with id : " + userId));
        Courses course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourcesNotFound("Course not found with id: " + courseId));

        AnalyticsLog analyticsLog = new AnalyticsLog();
        analyticsLog.setCourse(course);
        analyticsLog.setMetadata(metadataJson);
        analyticsLog.setUser(user);
        analyticsLog.setEventType(eventType);
        analyticsLog.setEventTimestamp(LocalDateTime.now());

        return mapToDTO(analyticsLog);
    }

    @Override
    public List<AnalyticsLogDTO> getUserProgressAnalytics(Long userId) {
        return analyticsLogRepository.findByUser_UserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlatformOverviewDTO getCourseEngagement(Long courseId) {

        Courses course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourcesNotFound("Course not found!"));

        Long totalUsers = analyticsLogRepository.countDistinctUserByCourseId(courseId);
        Long totalCompletions = analyticsLogRepository.countCompletionsByCourseId(courseId);
        Long activeUsers = analyticsLogRepository.countActiveUsersByCourseId(courseId);

        // ✅ Safe division: avoid Infinity / NaN
        double completionRate = (totalUsers != null && totalUsers > 0 && totalCompletions != null)
                ? ((double) totalCompletions / totalUsers) * 100
                : 0.00;

        PlatformOverviewDTO overview = new PlatformOverviewDTO();
        overview.setActiveUsers(activeUsers != null ? activeUsers : 0L);
        overview.setTotalUsers(totalUsers != null ? totalUsers : 0L);
        overview.setTotalCourse(1L);
        overview.setAverageCompletionRate(Double.isFinite(completionRate) ? completionRate : 0.00);
        overview.setTotalCompletion(totalCompletions != null ? totalCompletions : 0L);
        overview.setTopCourses(null);
        overview.setTimestamp(LocalDateTime.now());
        return overview;
    }

    @Override
    public PlatformOverviewDTO getPlatformOverview() {
        Long totalUsers = userRepository.count();
        Long activeUsers = analyticsLogRepository.countDistinctActiveUsers();
        Long totalCourses = courseRepository.count();
        Long totalCompletions = analyticsLogRepository.countByEventType("COURSE_COMPLETION");

        // ✅ Correct formula and safe division
        double completionRate = (totalCompletions != null && totalCompletions > 0 && totalCourses != null && totalCourses > 0)
                ? ((double) totalCompletions / totalCourses) * 100
                : 0.0;

        List<String> topCourses = analyticsLogRepository.findTopCoursesByEngagement();

        PlatformOverviewDTO dto = new PlatformOverviewDTO();
        dto.setTopCourses(topCourses);
        dto.setTotalCompletion(totalCompletions != null ? totalCompletions : 0L);
        dto.setActiveUsers(activeUsers != null ? activeUsers : 0L);
        dto.setTotalCourse(totalCourses != null ? totalCourses : 0L);
        dto.setTimestamp(LocalDateTime.now());
        dto.setAverageCompletionRate(Double.isFinite(completionRate) ? completionRate : 0.00);
        dto.setTotalUsers(totalUsers != null ? totalUsers : 0L);

        return dto;
    }

    // Helper mapper
    private AnalyticsLogDTO mapToDTO(AnalyticsLog entity) {
        AnalyticsLogDTO dto = new AnalyticsLogDTO();
        dto.setAnalyticLogId(entity.getAnalyticsId());
        dto.setCourseId(entity.getCourse().getCourseId());
        dto.setCourseTitle(entity.getCourse().getCourseTitle());
        dto.setEventTimestamp(entity.getEventTimestamp());
        dto.setUserId(entity.getUser().getUserId());
        dto.setMetaData(entity.getMetadata());
        dto.setEventType(entity.getEventType());
        return dto;
    }
}