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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AnalyticsLogServiceImple implements AnalyticsLogService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AnalyticsLogRepository analyticsLogRepository;

    @Override
    public AnalyticsLogDTO logEvent(Long userId, Long courseId, String eventType, String metadataJson) {
        Users user=userRepository.findById(userId)
                .orElseThrow(()-> new ResourcesNotFound(("User not found with id : "+userId)));
        Courses course=courseRepository.findById(courseId)
                .orElseThrow(()->new ResourcesNotFound("Course not found with id: "+courseId));

        AnalyticsLog analyticsLog =new AnalyticsLog();
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

        Courses course=courseRepository.findById(courseId)
                .orElseThrow(()->new ResourcesNotFound("Course not found!"));

        Long totalUsers=analyticsLogRepository.countDistinctUserByCourseId(courseId);
        Long totalCompletions=analyticsLogRepository.countCompletionsByCourseId(courseId);
        Long activeUsers=analyticsLogRepository.countActiveUsersByCourseId(courseId);

        double completionRate=(totalUsers!=null && totalUsers>0)?((double) totalCompletions/totalUsers)*100:0.00;

        PlatformOverviewDTO overview=new PlatformOverviewDTO();
        overview.setActiveUsers(activeUsers);
        overview.setTotalUsers(totalUsers);
        overview.setTotalCourse(1L);
        overview.setAverageCompletionRate(completionRate);
        overview.setTotalCompletion(totalCompletions);
        overview.setTopCourses(null);
        overview.setTimestamp(LocalDateTime.now());
        return overview;
    }

    @Override
    public PlatformOverviewDTO getPlatformOverview() {
        Long totalUsers=userRepository.count();
        Long activeUsers=analyticsLogRepository.countDistinctActiveUsers();
        Long totalCourse=courseRepository.count();
        Long totalCompletions=analyticsLogRepository.countByEventType("COURSE_COMPLETION");

        double courseCompletionRate=(totalCourse>0)?(double) totalCourse/totalCompletions:0.00;

        List<String> topCourses=analyticsLogRepository.findTopCoursesByEngagement();

        PlatformOverviewDTO dto=new PlatformOverviewDTO();

        dto.setTopCourses(topCourses);
        dto.setTotalCompletion(totalCompletions);
        dto.setActiveUsers(activeUsers);
        dto.setTotalCourse(totalCourse);
        dto.setTimestamp(LocalDateTime.now());
        dto.setAverageCompletionRate(courseCompletionRate);
        dto.setTotalUsers(totalUsers);

        return dto;
    }
    //mapping helper

    private AnalyticsLogDTO mapToDTO(AnalyticsLog entity){
        AnalyticsLogDTO dto=new AnalyticsLogDTO();
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
