package com.project.jagoga.user.domain;

public interface Encoder {

    public String encrypt(String password);

    public boolean isMatch(String password, String hashedPassword);
}