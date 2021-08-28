package com.project.jagoga.accommodation.domain;

import com.project.jagoga.accommodation.domain.address.City;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository {

    Accommodation save(Accommodation accommodation);

    Accommodation update(Accommodation accommodation);

    Long delete(long accommodationId);

    List<Accommodation> findAll();

    Optional<Accommodation> findById(long accommodationId);

    Optional<Accommodation> findByName(String accommodationName);

    void deleteAll();

    List<Accommodation> findByCategoryId(long accommodationId);
}
