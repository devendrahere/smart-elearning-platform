package com.edusmart.repository;

import com.edusmart.entity.Courses;
import com.edusmart.entity.Progress;
import com.edusmart.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress,Long> {
}
