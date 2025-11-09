package com.edusmart.service.ui;

import com.edusmart.dto.AnnouncementDTO;
import com.edusmart.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UIAnnouncementService {

    @Autowired
    private AnnouncementService announcementService;

    public List<AnnouncementDTO> getAnnouncementsForCourse(Long courseID) {
        return announcementService.getAnnouncementsForCourse(courseID);
    }

}
