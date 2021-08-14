package com.project.jagoga.user.domain;

public interface PasswordEncoder {

    public String encrypt(String password);

    public boolean isMatch(String password, String hashedPassword);
}