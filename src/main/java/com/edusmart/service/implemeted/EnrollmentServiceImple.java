package com.edusmart.service.implemeted;

import com.edusmart.dto.*;
import com.edusmart.entity.Courses;
import com.edusmart.entity.Enrollments;
import com.edusmart.entity.Users;
import com.edusmart.repository.CourseRepository;
import com.edusmart.repository.EnrollmentRepository;
import com.edusmart.repository.UserRepository;
import com.edusmart.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImple implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;


    @Override
    public EnrollmentsDTO enrollStudent(Long userId, Long courseId) {
        Users user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found with id: "+userId));

        Courses course=courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found with id: "+courseId));

        if(enrollmentRepository.existsByUsersUserIdAndCoursesCourseId(userId,courseId)){
            throw new RuntimeException("User already enrolled in this course.");
        }
//        if(!user.getRoles().){
//            throw new RuntimeException("User is not eligible to enroll due to role issue");
//        }
        Enrollments enrollment=new Enrollments();
        enrollment.setUsers(user);
        enrollment.setCourses(course);
        enrollment.setEnrolledAt(LocalDateTime.now());
        enrollment.setProgress(0.00);
        enrollment.setStatus("ACTIVE");

        Enrollments saved=enrollmentRepository.save(enrollment);
        return mapToDTO(saved);
    }

    @Override
    public boolean isStudentEnrolled(Long userId, Long courseId) {
        return enrollmentRepository.existsByUsersUserIdAndCoursesCourseId(userId,courseId);
    }

    @Override
    public void unenrollStudent(Long userId, Long courseId) {
        Enrollments enrollment= enrollmentRepository.findAll()
                .stream()
                .filter(e-> e.getUsers().getUserId()==userId && e.getCourses().getCourseId()==courseId)
                .findFirst()
                .orElseThrow(()->new RuntimeException("Enrollment Not found !"));

        enrollmentRepository.delete(enrollment);
    }

    @Override
    public List<CourseDTO> getCoursesByStudent(Long userId) {
        return enrollmentRepository.findByUsersUserId(userId)
                .stream()
                .map(e->mapToCourseDTO(e.getCourses()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getStudentsByCourse(Long courseId) {
        return enrollmentRepository.findByCoursesCourseId(courseId)
                .stream()
                .map(e->mapToUserDTO(e.getUsers()))
                .collect(Collectors.toList());
    }


    //mapping helper

    private EnrollmentsDTO mapToDTO(Enrollments e){
        EnrollmentsDTO dto = new EnrollmentsDTO();

        dto.setEnrollmentId(e.getEnrollmentId());
        dto.setEnrolledAt(e.getEnrolledAt());
        dto.setProgress(e.getProgress());
        dto.setStatus(e.getStatus());

        //adding user / student
        UserSummaryDTO userSummary= new UserSummaryDTO();
        userSummary.setUserId(e.getUsers().getUserId());
        userSummary.setName(e.getUsers().getUsername());
        userSummary.setEmail(e.getUsers().getEmail());
        dto.setStudent(userSummary);

        //adding course
        CourseSummaryDTO courseSummary=new CourseSummaryDTO();
        courseSummary.setCourseId(e.getCourses().getCourseId());
        courseSummary.setCourseDescription(e.getCourses().getCourseDescription());
        courseSummary.setCourseName(e.getCourses().getCourseTitle());
        if(e.getCourses().getCategory()!=null && !e.getCourses().getCategory().isEmpty()){
            Set<CategoryDTO> categories=e.getCourses().getCategory().stream()
                    .map(cat ->{
                        CategoryDTO DTO=new CategoryDTO();
                        DTO.setCategoryId(cat.getCategoryId());
                        DTO.setName(cat.getName());
                        DTO.setDescription(cat.getDescription());
                        return DTO;
                    } ).collect(Collectors.toSet());
            courseSummary.setCategoryName(categories);
        }
        courseSummary.setCreatedAt(e.getCourses().getCreatedAt());
        dto.setCourse(courseSummary);

        return dto;

    }

    private CourseDTO mapToCourseDTO(Courses course){
        CourseDTO dto=new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setCourseTitle(course.getCourseTitle());
        dto.setCourseDescription(course.getCourseDescription());
        dto.setCreatedAt(course.getCreatedAt());

        return dto;
    }

    private UserDTO mapToUserDTO(Users user){
        UserDTO dto=new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        return dto;

    }
}
