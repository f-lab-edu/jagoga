package com.project.jagoga.accommodation.domain;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository {

    Long save(Accommodation accommodation);

    Long delete(Long accommodationId);

    List<Accommodation> findAll();

    Optional<Accommodation> findById(Long accommodationId);

    Optional<Accommodation> findByName(String accommodationName);
}
