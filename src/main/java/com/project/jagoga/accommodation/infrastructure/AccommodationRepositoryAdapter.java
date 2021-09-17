package com.project.jagoga.accommodation.infrastructure;

import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccommodationRepositoryAdapter implements AccommodationRepository {

    private final JpaAccommodationRepository jpaAccommodationRepository;

    @Override
    public Accommodation save(Accommodation accommodation) {
        return jpaAccommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation update(Accommodation accommodation) {
        return jpaAccommodationRepository.save(accommodation);
    }

    @Override
    public Long delete(long accommodationId) {
        Accommodation accommodation = jpaAccommodationRepository.findById(accommodationId).get();
        jpaAccommodationRepository.delete(accommodation);
        return accommodationId;
    }

    @Override
    public List<Accommodation> findAll() {
        return jpaAccommodationRepository.findAll();
    }

    @Override
    public Optional<Accommodation> findById(long accommodationId) {
        return jpaAccommodationRepository.findById(accommodationId);
    }

    @Override
    public Optional<Accommodation> findByAccommodationName(String accommodationName) {
        return jpaAccommodationRepository.findByAccommodationName(accommodationName);
    }

    @Override
    public void deleteAll() {
        jpaAccommodationRepository.deleteAll();
    }
}
