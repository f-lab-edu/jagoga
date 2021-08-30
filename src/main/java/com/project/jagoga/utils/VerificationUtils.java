package com.project.jagoga.utils;

import com.project.jagoga.exception.user.ForbiddenException;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.Role;

public class VerificationUtils {

    public static void verifyPermission(AuthUser loginUser, long userId) {
        if ((loginUser.getRole() != Role.ADMIN) && !loginUser.getId().equals(userId)) {
            throw new ForbiddenException();
        }
    }
}
