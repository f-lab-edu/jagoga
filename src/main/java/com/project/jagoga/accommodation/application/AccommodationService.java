package com.project.jagoga.accommodation.application;

import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationRepository;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import com.project.jagoga.exception.accommodation.DuplicatedAccommodationException;
import com.project.jagoga.exception.accommodation.NotExistAccommodationException;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.utils.VerificationUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    public Accommodation saveAccommodation(AccommodationRequestDto accommodationRequestDto, AuthUser loginUser) {
        VerificationUtils.verifyOwnerPermission(loginUser, loginUser.getId());
        Accommodation accommodation = accommodationRequestDto.toEntity(loginUser.getId());
        validateDuplicatedAccommodation(accommodation);
        return accommodationRepository.save(accommodation);
    }

    public Accommodation updateAccommodation(
        long accommodationId,
        AccommodationRequestDto accommodationRequestDto,
        AuthUser loginUser
    ) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
            .orElseThrow(NotExistAccommodationException::new);
        long ownerId = accommodation.getOwnerId();
        VerificationUtils.verifyOwnerPermission(loginUser, ownerId);
        Accommodation updatedAccommodation = accommodation.update(
            accommodationRequestDto.getAccommodationName(),
            accommodationRequestDto.getPhoneNumber(),
            accommodationRequestDto.getCity().getId(),
            accommodationRequestDto.getAccommodationType(),
            accommodationRequestDto.getAccommodationName(),
            accommodationRequestDto.getInformation());
        return accommodationRepository.update(updatedAccommodation);
    }

    public Long deleteAccommodation(long accommodationId, AuthUser loginUser) {
        long ownerId = accommodationRepository.findById(accommodationId).get().getOwnerId();
        VerificationUtils.verifyOwnerPermission(loginUser, ownerId);
        accommodationRepository.findById(accommodationId)
            .ifPresentOrElse(
                a -> accommodationRepository.delete(accommodationId),
                () -> {
                    throw new NotExistAccommodationException();
                });
        return accommodationId;
    }

    public Accommodation getAccommodationById(long accommodationId) {
        return accommodationRepository.findById(accommodationId)
            .orElseThrow(NotExistAccommodationException::new);
    }

    public List<Accommodation> getAccommodationAllList() {
        return accommodationRepository.findAll();
    }

    public void deleteAll() {
        accommodationRepository.deleteAll();
    }

    private void validateDuplicatedAccommodation(Accommodation accommodation) {
        accommodationRepository.findByAccommodationName(accommodation.getAccommodationName())
            .ifPresent(a -> {
                throw new DuplicatedAccommodationException();
            });
    }
}
