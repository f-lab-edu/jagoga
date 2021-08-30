package com.project.jagoga.user.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    해당 어노테이션을 컨트롤러 메서드에 사용하면
    컨트롤러 메서드가 호출되기 전, 로그인 인증 유무를 확인합니다.
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginCheck {
}
