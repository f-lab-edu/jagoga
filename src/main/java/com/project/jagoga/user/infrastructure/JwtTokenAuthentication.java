package com.project.jagoga.user.infrastructure;

import com.project.jagoga.exception.user.NotFoundUserException;
import com.project.jagoga.exception.user.UserAuthenticationFailException;
import com.project.jagoga.user.domain.Authentication;
import com.project.jagoga.user.domain.PasswordEncoder;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenAuthentication implements Authentication {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        User foundUser = userRepository.getByEmail(loginRequestDto.getEmail()).orElseThrow(NotFoundUserException::new);

        if (!passwordEncoder.isMatch(loginRequestDto.getPassword(), foundUser.getPassword())) {
            throw new UserAuthenticationFailException();
        }

        return createToken(foundUser);
    }

    private String createToken(User user) {
        // Header
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        // Payload
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("email", user.getEmail());

        // 만료기간 30분
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + (1000 * 60L * 30));
        payloads.put("exp", expireTime);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject("userToken")
                .setClaims(payloads)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }
}
