package com.project.jagoga.accommodation.infrastructure.address;

import com.project.jagoga.accommodation.domain.address.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStateRepository extends JpaRepository<State, Long> {
}
