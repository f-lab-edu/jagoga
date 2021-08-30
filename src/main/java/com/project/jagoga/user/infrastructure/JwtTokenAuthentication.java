package com.project.jagoga.user.infrastructure;

import com.project.jagoga.exception.user.ExpiredTokenException;
import com.project.jagoga.exception.user.NotFoundUserException;
import com.project.jagoga.exception.user.UnAuthorizedException;
import com.project.jagoga.exception.user.UserAuthenticationFailException;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.Authentication;
import com.project.jagoga.user.domain.PasswordEncoder;
import com.project.jagoga.user.domain.Role;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.domain.UserRepository;
import com.project.jagoga.user.presentation.dto.request.LoginRequestDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenAuthentication implements Authentication {

    private final String secretKey;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtTokenAuthentication(
        @Value("${jwt.secret}") String secretKey,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.secretKey = secretKey;
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

    @Override
    public Optional<AuthUser> getLoginUser(String token) {
        Jws<Claims> claims = tokenParsing(token);

        Long id = claims.getBody().get("id", Long.class);
        String email = claims.getBody().get("email", String.class);
        String roleValue = claims.getBody().get("role", String.class);
        Role role = Role.valueOf(roleValue);
        return Optional.of(AuthUser.createInstance(id, email, role));
    }

    public void verifyLogin(String token) {
        tokenParsing(token);
    }

    /*
        토큰 파싱 및 검증을 진행한다
        실패 시 에러가 발생한다.
     */
    private Jws<Claims> tokenParsing(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token);
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
        payloads.put("id", user.getId());
        payloads.put("email", user.getEmail());
        payloads.put("role", user.getRole());

        // 만료기간 30분
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + (1000 * 60L * 30));
        payloads.put("exp", expireTime);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject("userToken")
                .setClaims(payloads)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }
}
