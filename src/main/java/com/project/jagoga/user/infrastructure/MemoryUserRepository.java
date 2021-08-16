package com.project.jagoga.user.infrastructure;

import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static ConcurrentHashMap<Long, User> userMap = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(); // 초기값이 0인 AtomicLong을 생성

    @Override
    public User save(User user) {
        user.setId(sequence.incrementAndGet());
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean existsByEmail(String email) {
        for (Long key : userMap.keySet()) {
            User user = userMap.get(key);
            if(StringUtils.equals(user.getEmail(), email)) {
                return true;
            }
        }
        return false;
    }
}
