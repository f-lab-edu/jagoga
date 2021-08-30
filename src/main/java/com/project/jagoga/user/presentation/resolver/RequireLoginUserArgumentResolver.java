package com.project.jagoga.user.presentation.resolver;

import com.project.jagoga.exception.user.UnAuthorizedException;
import com.project.jagoga.user.domain.Authentication;
import com.project.jagoga.user.domain.RequireLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class RequireLoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final Authentication authentication;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequireLoginUser.class);
    }

    @Override
    public Object resolveArgument(
        MethodParameter methodParameter,
        ModelAndViewContainer modelAndViewContainer,
        NativeWebRequest nativeWebRequest,
        WebDataBinderFactory webDataBinderFactory
    ) throws Exception {
        String token = nativeWebRequest.getHeader(HttpHeaders.AUTHORIZATION);
        return authentication.getLoginUser(token).orElseThrow(UnAuthorizedException::new);
    }
}
