package com.edusmart.repository;

import com.edusmart.entity.AnalyticsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyticsLogRepository extends JpaRepository<AnalyticsLog, Long> {

    List<AnalyticsLog> findByUser_UserId(Long userId);
    List<AnalyticsLog> findByCourse_CourseId(Long courseId);
    List<AnalyticsLog> findByEventType(String eventType);

    @Query("SELECT COUNT(a) FROM AnalyticsLog a WHERE a.course.id = :courseId AND a.eventType = 'COURSE_COMPLETION'")
    Long countCompletionsByCourseId(Long courseId);

    @Query(value = "SELECT COUNT(DISTINCT user_id) FROM analytics_log WHERE course_id = :courseId AND event_timestamp >= NOW() - INTERVAL '7 days'", nativeQuery = true)
    Long countActiveUsersByCourseId(Long courseId);

    // ✅ FIXED
    @Query("SELECT COUNT(DISTINCT a.user.userId) FROM AnalyticsLog a WHERE a.course.courseId = :courseId")
    Long countDistinctUserByCourseId(Long courseId);

    @Query(value = "SELECT COUNT(DISTINCT user_id) FROM analytics_log WHERE event_timestamp >= NOW() - INTERVAL '7 days'", nativeQuery = true)
    Long countDistinctActiveUsers();

    @Query("SELECT COUNT(a) FROM AnalyticsLog a WHERE a.eventType = :eventType")
    Long countByEventType(String eventType);

    // ✅ FIXED (native)
    @Query(value = """
    SELECT c.course_title
    FROM analytics_log a
    JOIN courses c ON a.course_id = c.course_id
    GROUP BY c.course_title
    ORDER BY COUNT(*) DESC
    LIMIT 5
    """, nativeQuery = true)
    List<String> findTopCoursesByEngagement();


    @Query("SELECT a.course.id, COUNT(a) FROM AnalyticsLog a GROUP BY a.course.id")
    List<Object[]> countLogsGroupedByCourse();
}

