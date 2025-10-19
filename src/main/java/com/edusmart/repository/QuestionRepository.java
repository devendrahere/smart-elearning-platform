package com.edusmart.repository;

import com.edusmart.entity.Question;
import com.edusmart.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question ,Long> {

}
