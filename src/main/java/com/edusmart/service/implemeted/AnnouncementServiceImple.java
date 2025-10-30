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
