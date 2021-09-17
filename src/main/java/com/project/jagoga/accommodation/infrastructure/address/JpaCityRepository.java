package com.project.jagoga.accommodation.infrastructure.address;

import com.project.jagoga.accommodation.domain.address.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCityRepository extends JpaRepository<City, Long> {
}
