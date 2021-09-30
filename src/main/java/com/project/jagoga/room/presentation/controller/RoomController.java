package com.project.jagoga.room.presentation.controller;

import com.project.jagoga.exception.dto.ApiResponse;
import com.project.jagoga.room.application.RoomService;
import com.project.jagoga.room.domain.Room;
import com.project.jagoga.room.presentation.dto.RoomCreateRequestDto;
import com.project.jagoga.room.presentation.dto.RoomResponseDto;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.LoginCheck;
import com.project.jagoga.user.domain.RequireLoginUser;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @LoginCheck
    @PostMapping("/api/accommodation/{accommodationId}/roomtype/{roomtypeId}/room")
    public ApiResponse<RoomResponseDto> registerRoom(
        @PathVariable final long accommodationId,
        @PathVariable final long roomtypeId,
        @Valid @RequestBody final RoomCreateRequestDto roomCreateRequestDto,
        @RequireLoginUser AuthUser loginUser
    ) {
        Room room = roomService.registerRoom(accommodationId, roomtypeId, roomCreateRequestDto, loginUser);
        return ApiResponse.createSuccess(RoomResponseDto.createInstance(room));
    }
}
