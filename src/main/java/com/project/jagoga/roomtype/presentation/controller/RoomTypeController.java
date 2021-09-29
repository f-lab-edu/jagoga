package com.project.jagoga.roomtype.presentation.controller;

import com.project.jagoga.exception.dto.ApiResponse;
import com.project.jagoga.roomtype.application.RoomTypeService;
import com.project.jagoga.roomtype.domain.RoomType;
import com.project.jagoga.roomtype.presentation.dto.RoomTypeCreateRequestDto;
import com.project.jagoga.roomtype.presentation.dto.RoomTypeResponseDto;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.LoginCheck;
import com.project.jagoga.user.domain.RequireLoginUser;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @LoginCheck
    @PostMapping("/api/accommodation/{accommodationId}/roomtype")
    public ApiResponse<RoomTypeResponseDto> registerRoomType(
        @PathVariable final long accommodationId,
        @Valid @RequestBody final RoomTypeCreateRequestDto roomTypeCreateRequestDto,
        @RequireLoginUser AuthUser loginUser
    ) {
        RoomType roomType = roomTypeService.registerRoomType(accommodationId, roomTypeCreateRequestDto, loginUser);
        return ApiResponse.createSuccess(RoomTypeResponseDto.createInstance(roomType));
    }
}
