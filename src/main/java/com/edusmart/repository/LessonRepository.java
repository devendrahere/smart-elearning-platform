package com.edusmart.repository;

import com.edusmart.entity.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lessons,Long> {
    List<Lessons> findLessonsByCoursesCourseId(Long courseId);
}
