package com.edusmart.repository;


import com.edusmart.entity.Announcements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcements,Long> {
    List<Announcements> findByCourse_CourseIdOrderByCreatedAtDesc(Long courseId);
}
