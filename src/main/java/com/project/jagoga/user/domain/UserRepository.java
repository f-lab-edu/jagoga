package com.project.jagoga.user.domain;

public interface UserRepository {

    public User save(User user);

    public boolean existsByEmail(String email);
}
