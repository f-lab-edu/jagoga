package com.project.jagoga.utils;

import com.project.jagoga.exception.user.ForbiddenException;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VerificationUtils {

    public static void verifyOwnerPermission(AuthUser loginUser, long userId) {
        if ((loginUser.getRole() != Role.ADMIN)
            && (loginUser.getRole() != Role.OWNER || !loginUser.getId().equals(userId))) {
            throw new ForbiddenException();
        }
    }

    public static void verifyBasicPermission(AuthUser loginUser, long userId) {
        if ((loginUser.getRole() != Role.ADMIN) && !loginUser.getId().equals(userId)) {
            throw new ForbiddenException();
        }
    }
}
