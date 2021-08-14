package com.project.jagoga.domain.user.dao;

import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static ConcurrentHashMap<Long, User> userMap = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        user.setId(++sequence);
        userMap.put(user.getId(), user);
        return user;
    }
}
