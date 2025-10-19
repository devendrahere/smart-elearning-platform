package com.edusmart.repository;

import com.edusmart.entity.Courses;
import com.edusmart.entity.Enrollments;
import com.edusmart.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollments,Long> {
    boolean existsByUsersUserIdAndCoursesCourseId(Long userId,Long CourseId);
    List<Enrollments> findByUsersUserId(Long userId);
    List<Enrollments> findByCoursesCourseId(Long courseId);
}
