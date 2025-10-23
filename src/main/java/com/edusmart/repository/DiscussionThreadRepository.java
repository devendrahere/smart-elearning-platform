package com.edusmart.repository;

import com.edusmart.entity.DiscussionThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionThreadRepository extends JpaRepository<DiscussionThread,Long> {
    List<DiscussionThread> findByCourse_CourseId(Long courseID);
}
