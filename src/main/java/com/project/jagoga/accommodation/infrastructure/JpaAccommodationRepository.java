package com.project.jagoga.accommodation.infrastructure;

import com.project.jagoga.accommodation.domain.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAccommodationRepository extends JpaRepository<Accommodation, Long> {

    Optional<Accommodation> findByAccommodationName(String accommodationName);
}
