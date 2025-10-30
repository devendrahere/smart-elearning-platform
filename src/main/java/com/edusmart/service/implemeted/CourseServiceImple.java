package com.edusmart.service.implemeted;

import com.edusmart.dto.CategoryDTO;
import com.edusmart.dto.CourseDTO;
import com.edusmart.dto.UserSummaryDTO;
import com.edusmart.entity.Categories;
import com.edusmart.entity.Courses;
import com.edusmart.entity.Users;
import com.edusmart.exception.ResourcesNotFound;
import com.edusmart.repository.CategoryRepository;
import com.edusmart.repository.CourseRepository;
import com.edusmart.repository.UserRepository;
import com.edusmart.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImple implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        //fetching instructor
        Users instructor=userRepository.findById(courseDTO.getInstructor().getUserId())
                .orElseThrow(()->new ResourcesNotFound("Instructor Not found with Id "+courseDTO.getInstructor().getUserId()));
        Courses course=mapToEntity(courseDTO);
        //adding instructor to course
        course.setInstructor(instructor);
        course.setCreatedAt(LocalDateTime.now());
        if(courseDTO.getCategories()!=null && !courseDTO.getCategories().isEmpty()){
            Set<Categories> categoriesEntity=courseDTO.getCategories().stream()
                    .map(categoryDTO -> categoryRepository.findById(categoryDTO.getCategoryId()).orElseThrow(()->new RuntimeException("Category not found with Id: "+categoryDTO.getCategoryId())))
                    .collect(Collectors.toSet());
            course.setCategory(categoriesEntity);
        }
        Courses saved=courseRepository.save(course);
        return mapToDTO(saved);
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Courses course= courseRepository.findById(id)
                .orElseThrow(()->new ResourcesNotFound("No course found with id: "+id));

        return mapToDTO(course);
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {

        Courses existing = courseRepository.findById(id)
                .orElseThrow(()->new ResourcesNotFound("No course found with id: "+id));
        existing.setCourseTitle(courseDTO.getCourseTitle());
        existing.setCourseDescription(courseDTO.getCourseDescription());
        existing.setCreatedAt(LocalDateTime.now());
        if(courseDTO.getCategories()!=null && !courseDTO.getCategories().isEmpty()){
            Set<Categories> categoryEntity=courseDTO.getCategories().stream()
                    .map(categoryDTO -> categoryRepository.findById(categoryDTO.getCategoryId()).orElseThrow(()->new ResourcesNotFound("No Category with id found: "+categoryDTO.getCategoryId())))
                    .collect(Collectors.toSet());
            existing.setCategory(categoryEntity);
        }
        if (courseDTO.getInstructor()!=null){
            Users instructor=userRepository.findById(courseDTO.getInstructor().getUserId())
                    .orElseThrow(()->new ResourcesNotFound("No instructor found with id: "+courseDTO.getInstructor().getUserId()));

            existing.setInstructor(instructor);
        }

        Courses updated=courseRepository.save(existing);
        return mapToDTO(updated);
    }

    @Override
    public List<CourseDTO> getAllCourse() {
        return courseRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCourse(Long courseId) {
        if(!courseRepository.existsById(courseId)){
            throw  new ResourcesNotFound("Course with id "+courseId+" does not exists !");
        }
        courseRepository.deleteById(courseId);
    }

    //mapping helper

    private CourseDTO mapToDTO(Courses course){
        CourseDTO dto=new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setCourseTitle(course.getCourseTitle());
        dto.setCourseDescription(course.getCourseDescription());
        dto.setCreatedAt(course.getCreatedAt());

        if(course.getInstructor()!=null){
            UserSummaryDTO instructor= new UserSummaryDTO();
            instructor.setUserId(course.getInstructor().getUserId());
            instructor.setName(course.getInstructor().getUsername());
            instructor.setEmail(course.getInstructor().getEmail());
            dto.setInstructor(instructor);
        }
        if(course.getCategory()!=null){
            List<CategoryDTO> categoryDTOs=course.getCategory().stream().
                    map(cat->{
                        CategoryDTO dtocat= new CategoryDTO();
                        dtocat.setCategoryId(cat.getCategoryId());
                        dtocat.setName(cat.getName());
                        dtocat.setDescription(cat.getDescription());
                        return dtocat;
                    })
                    .collect(Collectors.toList());
            dto.setCategories(categoryDTOs);
        };
        return dto;
    }

    private Courses mapToEntity(CourseDTO dto){
        Courses course=new Courses();
        course.setCourseTitle(dto.getCourseTitle());
        course.setCourseDescription(dto.getCourseDescription());
        return course;
    }
}
