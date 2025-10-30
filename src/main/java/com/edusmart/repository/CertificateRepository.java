package com.edusmart.repository;

import com.edusmart.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,Long> {
    List<Certificate> findByUserId(Long userId);
    Optional<Certificate> findByCertificateCode(String code);
    boolean existsByUserIdAndCourseId(Long userId,Long courseId);
    List<Certificate> findByCourse_CourseId(Long courseId);
}
