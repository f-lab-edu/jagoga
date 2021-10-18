package com.project.jagoga.roominventory.presentation.controller;

import com.project.jagoga.roominventory.application.RoomInventoryService;
import com.project.jagoga.roominventory.presentation.dto.RoomInventoryAddRequestDto;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.LoginCheck;
import com.project.jagoga.user.domain.RequireLoginUser;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomInventoryController {

    private final RoomInventoryService roomInventoryService;

    @LoginCheck
    @PostMapping("/api/roomtypes/{roomTypeId}/roominventories")
    public void addInventory(
        @PathVariable final long roomTypeId,
        @Valid @RequestBody final RoomInventoryAddRequestDto roomInventoryAddRequestDto,
        @RequireLoginUser AuthUser loginUser
    ) {
        roomInventoryService.addInventory(roomTypeId, roomInventoryAddRequestDto, loginUser);
    }
}
