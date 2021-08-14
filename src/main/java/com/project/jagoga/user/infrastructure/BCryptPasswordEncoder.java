package com.project.jagoga.user.infrastructure;

import com.project.jagoga.user.domain.Encoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptEncoder implements Encoder {

    @Override
    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean isMatch(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
