package com.project.jagoga.accommodation.presentation.controller;

import static com.project.jagoga.accommodation.presentation.controller.AccommodationController.ACCOMMODATION_API_URI;

import com.project.jagoga.accommodation.application.AccommodationService;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import com.project.jagoga.accommodation.presentation.dto.AccommodationResponseDto;

import javax.validation.Valid;

import com.project.jagoga.exception.dto.ApiResponse;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.LoginCheck;
import com.project.jagoga.user.domain.RequireLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ACCOMMODATION_API_URI)
public class AccommodationController {

    public static final String ACCOMMODATION_API_URI = "/api/accommodation";

    private final AccommodationService accommodationService;

    @LoginCheck
    @PostMapping
    public ApiResponse<AccommodationResponseDto> createAccommodation(
        @Valid @RequestBody final AccommodationRequestDto accommodationRequestDto,
        @RequireLoginUser AuthUser loginUser
    ) {
        Accommodation accommodation =
            accommodationService.saveAccommodation(accommodationRequestDto, loginUser);
        return ApiResponse.createSuccess(AccommodationResponseDto.of(accommodation));
    }

    @LoginCheck
    @PutMapping("/{accommodationId}")
    public ApiResponse<AccommodationResponseDto> updateAccommodation(
        @PathVariable long accommodationId,
        @Valid @RequestBody final AccommodationRequestDto accommodationRequestDto,
        @RequireLoginUser AuthUser loginUser
    ) {
        Accommodation accommodation =
            accommodationService.updateAccommodation(accommodationId, accommodationRequestDto, loginUser);
        return ApiResponse.createSuccess(AccommodationResponseDto.of(accommodation));
    }

    @LoginCheck
    @DeleteMapping("/{accommodationId}")
    public ApiResponse<?> deleteAccommodation(
        @PathVariable long accommodationId,
        @RequireLoginUser AuthUser loginUser
    ) {
        accommodationService.deleteAccommodation(accommodationId, loginUser);
        return ApiResponse.createSuccessWithNoContent();
    }
}
