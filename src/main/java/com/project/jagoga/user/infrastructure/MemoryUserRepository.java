package com.project.jagoga.user.infrastructure;

import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.StringUtils;

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
    public User update(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(userMap.get(userId));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userMap.values().stream().filter(user -> StringUtils.equals(user.getEmail(), email)).findAny();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userMap.values().stream()
            .anyMatch(user -> StringUtils.equals(user.getEmail(), email));
    }

    @Override
    public void deleteAll() {
        userMap.clear();
    }
}
