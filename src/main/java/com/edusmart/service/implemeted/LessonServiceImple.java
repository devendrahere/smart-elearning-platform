package com.edusmart.service.implemeted;

import com.edusmart.dto.LessonDTO;
import com.edusmart.entity.Courses;
import com.edusmart.entity.Lessons;
import com.edusmart.exception.ResourcesNotFound;
import com.edusmart.repository.CourseRepository;
import com.edusmart.repository.LessonRepository;
import com.edusmart.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImple implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public LessonDTO createLesson(Long courseId, LessonDTO lessonDTO) {
        Courses courses=courseRepository.findById(courseId).orElseThrow(()->new ResourcesNotFound("No course found with id: "+courseId));

        Lessons lessons=mapToEntity(lessonDTO);

        lessons.setCourses(courses);
        lessons.setCreatedAt(LocalDateTime.now());
        lessons.setUpdatedAt(LocalDateTime.now());

        Lessons saved= lessonRepository.save(lessons);

        return mapToDTO(saved);
    }

    @Override
    public LessonDTO getLessonById(Long lessonId) {
        Lessons lessons=lessonRepository.findById(lessonId).orElseThrow(()->new ResourcesNotFound("No lesson found with lesson id: "+lessonId));

        return mapToDTO(lessons);
    }

    @Override
    public List<LessonDTO> getLessonByCourse(Long courseId) {
        return lessonRepository.findLessonsByCoursesCourseId(courseId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessonDTO updateLessonById(Long lessonId, LessonDTO lessonDTO) {
        Lessons existing= lessonRepository.findById(lessonId).orElseThrow(()->new ResourcesNotFound("No lesson found with Id: "+lessonId));

        existing.setLessonTitle(lessonDTO.getLessonTitle());
        existing.setContentUrl(lessonDTO.getContentUrl());
        existing.setLessonOrder(lessonDTO.getLessonOrder());
        existing.setUpdatedAt(LocalDateTime.now());

        Lessons updated=lessonRepository.save(existing);

        return mapToDTO(updated);
    }

    @Override
    public void deleteLesson(Long lessonId) {
        if(!lessonRepository.existsById(lessonId)){
            throw new ResourcesNotFound("Lesson does not exists with id: "+lessonId);
        }

        lessonRepository.deleteById(lessonId);
    }

    @Override
    public Courses getCourseByLessonId(Long lessonId){
        Lessons lesson=lessonRepository.findById(lessonId)
                .orElseThrow(()-> new ResourcesNotFound("Lesson not found"));
        return lesson.getCourses();
    }

    //mapping helper

    private LessonDTO mapToDTO(Lessons lesson){
        LessonDTO dto=new LessonDTO();
        dto.setLessonId(lesson.getLessonId());
        dto.setCreatedAt(lesson.getCreatedAt());
        dto.setLessonTitle(lesson.getLessonTitle());
        dto.setLessonOrder(lesson.getLessonOrder());
        dto.setContentUrl(lesson.getContentUrl());
        dto.setUpdatedAt(lesson.getUpdatedAt());
        dto.setCourseId(lesson.getCourses().getCourseId());
        return dto;
    }

    private Lessons mapToEntity(LessonDTO dto){
        Lessons lesson=new Lessons();
        lesson.setLessonTitle(dto.getLessonTitle());
        lesson.setLessonOrder(dto.getLessonOrder());
        lesson.setContentUrl(dto.getContentUrl());


        return lesson;
    }
}
