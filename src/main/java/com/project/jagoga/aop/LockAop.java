package com.project.jagoga.aop;

import com.project.jagoga.exception.roomtype.NotExistRoomTypeException;
import com.project.jagoga.roomtype.application.RoomTypeService;
import com.project.jagoga.roomtype.domain.RoomType;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LockAop {

    private final RoomTypeService roomTypeService;

    @Before(value = "@within(com.project.jagoga.aop.RoomTypeLock) || @annotation(com.project.jagoga.aop.RoomTypeLock)")
    public void roomtypeLock(JoinPoint jp) {

        Long roomTypeId = null;
        Object[] parameterValues = jp.getArgs();

        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();

        for (int i = 0; i < method.getParameters().length; i++) {
            String parameterName = method.getParameters()[i].getName();
            if (parameterName.equals("roomTypeId")) {
                roomTypeId = (Long) parameterValues[i];
            }
        }

        if (roomTypeId == null) {
            throw new NotExistRoomTypeException();
        }

        RoomType roomType = roomTypeService.getRoomTypeById(roomTypeId);
    }
}
