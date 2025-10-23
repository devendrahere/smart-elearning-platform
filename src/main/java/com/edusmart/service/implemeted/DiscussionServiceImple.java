package com.edusmart.service.implemeted;

import com.edusmart.dto.DiscussionDTO;
import com.edusmart.dto.DiscussionThreadDTO;
import com.edusmart.entity.Courses;
import com.edusmart.entity.DiscussionThread;
import com.edusmart.entity.Discussions;
import com.edusmart.entity.Users;
import com.edusmart.repository.CourseRepository;
import com.edusmart.repository.DiscussionRepository;
import com.edusmart.repository.DiscussionThreadRepository;
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

    @Autowired
    private DiscussionThreadRepository threadRepository;

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

    @Override
    public DiscussionThreadDTO createThread(Long courseId, String title, Long createdBy) {
        Courses course=courseRepository.findById(courseId)
                .orElseThrow(()->new RuntimeException("Course not Found with course id :"+courseId ));
        Users creator=userRepository.findById(createdBy)
                .orElseThrow(()->new RuntimeException("User not found with user id :"+createdBy));

        DiscussionThread thread=new DiscussionThread();
        thread.setCourse(course);
        thread.setCreatedBy(creator);
        thread.setTitle(title);
        thread.setCreatedAt(LocalDateTime.now());

        DiscussionThread saved=threadRepository.save(thread);

        return mapToThreadDTO(saved);
    }

    @Override
    public List<DiscussionThreadDTO> getThreadByCourse(Long courseId) {
        return threadRepository.findByCourse_CourseId(courseId)
                .stream()
                .map(this::mapToThreadDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DiscussionDTO> getMessageByThread(Long threadId) {
        return discussionRepository.findByThread_ThreadIdOrderByCreatedAtAsc(threadId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DiscussionDTO postMessage(Long threadId, DiscussionDTO messageDTO) {
        DiscussionThread thread=threadRepository.findById(threadId)
                .orElseThrow(()->new RuntimeException("Thread not found with Id: "+threadId));
        Users user=userRepository.findById(messageDTO.getUserId())
                .orElseThrow(()->new RuntimeException("User not found ."));

        Discussions message= new Discussions();
        message.setThread(thread);
        message.setUsers(user);
        message.setCourse(thread.getCourse());
        message.setContent(messageDTO.getContent());
        message.setCreatedAt(LocalDateTime.now());

        Discussions saved=discussionRepository.save(message);
        return mapToDTO(saved);
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

    private DiscussionThreadDTO mapToThreadDTO(DiscussionThread thread){
        DiscussionThreadDTO dto=new DiscussionThreadDTO();
        dto.setTitle(thread.getTitle());
        dto.setCreatedBy(thread.getCreatedBy().getUserId());
        dto.setCourseId(thread.getCourse().getCourseId());
        dto.setThreadId(thread.getThreadId());
        dto.setCreatedAt(thread.getCreatedAt());

        return dto;
    }
}
