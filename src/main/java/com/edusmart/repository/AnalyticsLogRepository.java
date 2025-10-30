package com.edusmart.repository;

import com.edusmart.entity.AnalyticsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyticsLogRepository extends JpaRepository<AnalyticsLog,Long> {
    List<AnalyticsLog> findByUser_UserId(Long userId);
    List<AnalyticsLog> findByCourse_CourseId(Long courseId);
    List<AnalyticsLog> findByEventType(String eventType);
    @Query("SELECT COUNT(a) FROM AnalyticsLog a WHERE a.course.id =:courseId AND a.eventType='COURSE_COMPLETION'")
    Long countCompletionsByCourseId(Long courseId);

    @Query("SELECT COUNT(DISTINCT a.user.userId) FROM AnalyticsLog a WHERE a.course.courseId =:courseId AND a.eventTimestamp >= CURRENT_DATE-7")
    Long countActiveUsersByCourseId(Long courseId);

    @Query("SELECT COUNT(DISTINCT a.user.userId) FROM AnalyticsLog a WHERE a.eventTimestamp>=CURRENT_DATE-7")
    Long countDistinctActiveUsers();

    @Query("SELECT COUNT(DISTINCT a.user.userId) FROM AnalyticsLog a WHERE a.course.courseId=:courseId")
    Long countDistinctUserByCourseId(Long courseId);

    @Query("SELECT COUNT(a) FROM AnalyticsLog a WHERE a.eventType= :eventType")
    Long countByEventType(String eventType);

    @Query("SELECT a.course.courseTitle FROM AnalyticsLog a GROUP BY a.course.courseTitle ORDER BY COUNT(a) DESC LIMIT 5")
    List<String> findTopCoursesByEngagement();

    @Query("SELECT a.course.id,COUNT(a) FROM AnalyticsLog a GROUP BY a.course.id")
    List<Object[]> countLogsGroupedByCourse();
}
