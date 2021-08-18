package com.project.jagoga.accommodation.application;

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

    public Long deleteAccommodation(Long AccommodationId) {
        accommodationRepository.findById(AccommodationId)
                .ifPresentOrElse(
                        p -> accommodationRepository.delete(AccommodationId),
                        () -> {
                            throw new NotExistAccommodationException();
                        });
        return AccommodationId;
    }

    public List<Accommodation> getAccommodationAllList() {
        return accommodationRepository.findAll();
    }

    private void validateDuplicatedAccommodation(Accommodation accommodation) {
        accommodationRepository.findByName(accommodation.getAccommodationName())
                .ifPresent(p -> {
                    throw new DuplicatedAccommodationException();
                });
    }
}
