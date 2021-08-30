package com.project.jagoga.accommodation.presentation.controller;

import static com.project.jagoga.accommodation.presentation.controller.AccommodationController.ACCOMMODATION_API_URI;
import com.project.jagoga.accommodation.application.AccommodationService;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import com.project.jagoga.accommodation.presentation.dto.AccommodationResponseDto;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping
    public ResponseEntity<Void> createAccommodation(
        @Valid @RequestBody final AccommodationRequestDto accommodationRequestDto) {
        Long accommodationId = accommodationService.saveAccommodation(accommodationRequestDto.toEntity());
        return ResponseEntity
            .created(URI.create(ACCOMMODATION_API_URI + "/" + accommodationId))
            .build();
    }

    @PutMapping("/{accommodationId}")
    public ResponseEntity<AccommodationResponseDto> updateAccommodation(
        @PathVariable long accommodationId,
        @Valid @RequestBody AccommodationRequestDto accommodationRequestDto) {
        AccommodationResponseDto accommodationResponseDto
            = accommodationService.updateAccommodation(accommodationId, accommodationRequestDto);
        return ResponseEntity
            .created(URI.create(ACCOMMODATION_API_URI + "/" + accommodationId))
            .body(accommodationResponseDto);
    }

    @DeleteMapping("/{accommodationId}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable long accommodationId) {
        accommodationService.deleteAccommodation(accommodationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<AccommodationResponseDto>> getAccommodationListByCategory(
        @PathVariable long categoryId) {
        List<AccommodationResponseDto> accommodationListByCategoryId
            = AccommodationResponseDto.listOf(
            accommodationService.getAccommodationListByCategoryId(categoryId));
        return ResponseEntity.ok(accommodationListByCategoryId);
    }
}
