package com.edusmart.service;


import java.util.Set;

public interface UserPresenceService {
    void userJoined(Long courseId,Long userId);
    void userLeft(Long courseId,Long userId);
    Set<Long> getOnlineUsers(Long courseId);
}
