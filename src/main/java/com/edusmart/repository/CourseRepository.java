package com.edusmart.repository;

import com.edusmart.entity.Courses;
import com.edusmart.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Courses,Long> {
//    List<Courses> findByInstructorId(Long userId);
//    List<Courses> findByCategoryCategoryId(Long categoryId);
}
