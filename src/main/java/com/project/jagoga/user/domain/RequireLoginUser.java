package com.project.jagoga.user.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    해당 어노테이션을 컨트롤러 메서드 파라미터에 사용하면
    컨트롤러 메서드가 호출되기 전, 인자값으로 AuthUser(로그인한 유저의 정보)를 전달합니다.
 */
@Target(value = {ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLoginUser {
}
