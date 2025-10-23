package com.edusmart.service.implemeted;

import com.edusmart.service.UserPresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.Object;
import java.util.Set;

public class UserPresenceServiceImple implements UserPresenceService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String getRedisKey(Long courseId){
        return "course:"+courseId+":onlineUsers";
    }

    @Override
    public void userJoined(Long courseId, Long userId) {
        redisTemplate.opsForSet().add(getRedisKey(courseId),userId);
    }

    @Override
    public void userLeft(Long courseId, Long userId) {
        redisTemplate.opsForSet().remove(getRedisKey(courseId),userId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Long> getOnlineUsers(Long courseId) {
        return (Set<Long>)(Set<?>) redisTemplate.opsForSet().members(getRedisKey(courseId)) ;
    }
}
