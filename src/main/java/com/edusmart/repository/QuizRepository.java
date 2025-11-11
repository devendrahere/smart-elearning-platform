package com.edusmart.repository;

import com.edusmart.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findByCourseCourseId(Long courseId);
    Optional<Quiz> findByCourse_CourseId(Long courseId);
}
