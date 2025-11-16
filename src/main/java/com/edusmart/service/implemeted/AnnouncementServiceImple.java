package com.edusmart.service.implemeted;

import com.edusmart.dto.AnnouncementDTO;
import com.edusmart.entity.Announcements;
import com.edusmart.entity.Courses;
import com.edusmart.entity.Users;
import com.edusmart.exception.ResourcesNotFound;
import com.edusmart.repository.AnnouncementRepository;
import com.edusmart.repository.CourseRepository;
import com.edusmart.repository.UserRepository;
import com.edusmart.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImple implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AnnouncementDTO createAnnouncement(Long courseId, String message, Long instructorId) {
        Users user =userRepository.findById(instructorId)
                .orElseThrow(()->new ResourcesNotFound("no user found with id : "+instructorId));
        Courses course= courseRepository.findById(courseId)
                .orElseThrow(()->new ResourcesNotFound("no course found with id : "+courseId));
        Announcements announcement= new Announcements();
        announcement.setCourse(course);
        announcement.setMessage(message);
        announcement.setPostedBy(user);
        announcement.setCreatedAt(LocalDateTime.now());

        Announcements saved= announcementRepository.save(announcement);

        return mapToDTO(saved);
    }

    @Override
    public List<AnnouncementDTO> getAnnouncementsForCourse(Long courseId) {
        return announcementRepository.findByCourse_CourseIdOrderByCreatedAtDesc(courseId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public boolean isInstructorOfCourse(Long courseId, Long userId) {
        Courses course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourcesNotFound("Course not found"));

        return course.getInstructor().getUserId().equals(userId);
    }

    @Override
    public void deleteAnnouncement(Long courseId, Long announcementId, Long userId) {
        Announcements announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new ResourcesNotFound("Announcement not found"));

        if (!announcement.getCourse().getCourseId().equals(courseId)) {
            throw new RuntimeException("Unauthorized delete attempt");
        }

        if (!announcement.getCourse().getInstructor().getUserId().equals(userId)) {
            throw new RuntimeException("Only instructor can delete announcements");
        }

        announcementRepository.delete(announcement);
    }


    //mapping helpers

    private AnnouncementDTO mapToDTO(Announcements announcement){
        AnnouncementDTO dto=new AnnouncementDTO();
        dto.setAnnouncementId(announcement.getAnnouncementId());
        dto.setCourseId(announcement.getCourse().getCourseId());
        dto.setCreatedAt(announcement.getCreatedAt());
        dto.setMessage(announcement.getMessage());
        dto.setPostedBy(announcement.getPostedBy().getUserId());
        dto.setPosterName(announcement.getPostedBy().getUsername());

        return dto;
    }
}
