package com.edusmart.service.implemeted;

import com.edusmart.dto.DiscussionDTO;
import com.edusmart.entity.Courses;
import com.edusmart.entity.Discussions;
import com.edusmart.entity.Users;
import com.edusmart.repository.CourseRepository;
import com.edusmart.repository.DiscussionRepository;
import com.edusmart.repository.UserRepository;
import com.edusmart.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscussionServiceImple implements DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public DiscussionDTO createDiscussion(DiscussionDTO discussionDTO) {
        Users user=userRepository.findById(discussionDTO.getUserId())
                .orElseThrow(()->new RuntimeException("No user found with id: "+discussionDTO.getUserId()));

        Courses course=courseRepository.findById(discussionDTO.getCourseId())
                .orElseThrow(()->new RuntimeException("No course found with id: "+discussionDTO.getCourseId()));

        Discussions discussion=new Discussions();
        discussion.setUsers(user);
        discussion.setCourse(course);
        discussion.setCreatedAt(LocalDateTime.now());
        discussion.setContent(discussionDTO.getContent());

        Discussions saved=discussionRepository.save(discussion);
        return mapToDTO(saved);
    }

    @Override
    public List<DiscussionDTO> getDiscussionsByCourse(Long courseId) {
        return discussionRepository.findByCourseCourseId(courseId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DiscussionDTO> getDiscussionsByUser(Long userId) {
        return discussionRepository.findByUsersUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDiscussion(Long discussionId) {
        if(!discussionRepository.existsById(discussionId)){
            throw  new RuntimeException("No discussion found by id: "+discussionId);
        }
        discussionRepository.deleteById(discussionId);
    }

    //mapping helper
    private DiscussionDTO mapToDTO(Discussions discussions){
        DiscussionDTO dto=new DiscussionDTO();
        dto.setDiscussionId(discussions.getDiscussion_id());
        dto.setContent(discussions.getContent());
        dto.setCreatedAt(discussions.getCreatedAt());
        dto.setUsername(discussions.getUsers().getUsername());
        dto.setCourseId(discussions.getCourse().getCourseId());

        return dto;
    }
}
