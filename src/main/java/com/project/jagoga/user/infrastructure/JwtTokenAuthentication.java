package com.project.jagoga.user.infrastructure;

import com.project.jagoga.exception.user.ExpiredTokenException;
import com.project.jagoga.exception.user.NotFoundUserException;
import com.project.jagoga.exception.user.UnAuthorizedException;
import com.project.jagoga.exception.user.UserAuthenticationFailException;
import com.project.jagoga.user.domain.Authentication;
import com.project.jagoga.user.domain.PasswordEncoder;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenAuthentication implements Authentication {

    private final String SECRET_KEY;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtTokenAuthentication(@Value("${jwt.secret}") String SECRET_KEY, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.SECRET_KEY = SECRET_KEY;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        User foundUser = userRepository.getByEmail(loginRequestDto.getEmail())
                .orElseThrow(NotFoundUserException::new);

        if (!passwordEncoder.isMatch(loginRequestDto.getPassword(), foundUser.getPassword())) {
            throw new UserAuthenticationFailException();
        }

        return createToken(foundUser);
    }

    public void verifyLogin(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(token); // 토큰 파싱 및 검증 진행, 실패 시 에러
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new UnAuthorizedException();
        }
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
