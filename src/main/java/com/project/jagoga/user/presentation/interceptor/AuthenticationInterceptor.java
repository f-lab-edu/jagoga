package com.project.jagoga.user.presentation.interceptor;

import com.project.jagoga.user.domain.Authentication;
import com.project.jagoga.user.domain.LoginCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final Authentication authentication;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (((HandlerMethod) handler).getMethodAnnotation(LoginCheck.class) == null) {
            return true;
        }
        verifyLogin(request);

        return true;
    }

    private void verifyLogin(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        authentication.verifyLogin(token);
    }
}
