package com.project.jagoga.accommodation.application;

import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import com.project.jagoga.accommodation.presentation.dto.AccommodationResponseDto;
import com.project.jagoga.exception.accommodation.DuplicatedAccommodationException;
import com.project.jagoga.exception.accommodation.NotExistAccommodationException;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    public Long saveAccommodation(Accommodation accommodation) {
        validateDuplicatedAccommodation(accommodation);
        return accommodationRepository.save(accommodation).getAccommodationId();
    }

    public AccommodationResponseDto updateAccommodation(long accommodationId, AccommodationRequestDto accommodationRequestDto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(NotExistAccommodationException::new);

        Accommodation updatedAccommodation = accommodation.update(
                accommodationRequestDto.getAccommodationName(),
                accommodationRequestDto.getPhoneNumber(),
                accommodationRequestDto.getCity(),
                accommodationRequestDto.getAccommodationType(),
                accommodationRequestDto.getAccommodationName(),
                accommodationRequestDto.getInformation());
        return AccommodationResponseDto.of(accommodationRepository.update(updatedAccommodation));
    }

    public Long deleteAccommodation(long AccommodationId) {
        accommodationRepository.findById(AccommodationId)
                .ifPresentOrElse(
                        a -> accommodationRepository.delete(AccommodationId),
                        () -> {
                            throw new NotExistAccommodationException();
                        });
        return AccommodationId;
    }

    public Accommodation getAccommodation(long accommodationId) {
        return accommodationRepository.findById(accommodationId)
                .orElseThrow(NotExistAccommodationException::new);
    }

    public List<Accommodation> getAccommodationAllList() {
        return accommodationRepository.findAll();
    }

    public List<Accommodation> getAccommodationListByCategoryId(long categoryId) {
        return accommodationRepository.findByCategoryId(categoryId);
    }

    private void validateDuplicatedAccommodation(Accommodation accommodation) {
        accommodationRepository.findByName(accommodation.getAccommodationName())
                .ifPresent(a -> {
                    throw new DuplicatedAccommodationException();
                });
    }
}
