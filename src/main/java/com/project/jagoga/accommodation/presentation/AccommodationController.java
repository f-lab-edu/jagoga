package com.project.jagoga.accommodation.presentation;

import com.project.jagoga.accommodation.application.AccommodationService;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import com.project.jagoga.accommodation.presentation.dto.AccommodationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.project.jagoga.accommodation.presentation.AccommodationController.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PRODUCT_API_URI)
public class AccommodationController {

    public static final String PRODUCT_API_URI = "/api/accommodation";

    private final AccommodationService accommodationService;

    @PostMapping
    public ResponseEntity<Void> createAccommodation(@RequestBody final AccommodationRequestDto accommodationRequestDto) {
        Long accommodationId = accommodationService.saveAccommodation(accommodationRequestDto.toEntity());
        return ResponseEntity
                .created(URI.create(PRODUCT_API_URI + "/" + accommodationId))
                .build();
    }

    @DeleteMapping("/{accommodationId}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long accommodationId) {
        accommodationService.deleteAccommodation(accommodationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AccommodationResponseDto>> getAccommodationAllList() {
        List<AccommodationResponseDto> accommodationAllList = AccommodationResponseDto.listOf(accommodationService.getAccommodationAllList());
        return ResponseEntity.ok(accommodationAllList);
    }
}
