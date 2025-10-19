package com.edusmart.repository;

import com.edusmart.entity.Courses;
import com.edusmart.entity.Discussions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussions,Long> {
    List<Discussions> findByCourseCourseId(Long courseId);
    List<Discussions> findByUsersUserId(Long userId);
}
