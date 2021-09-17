package com.project.jagoga.accommodation.infrastructure.address;

import com.project.jagoga.accommodation.domain.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaAddressRepository {

    private final JpaCityRepository jpaCityRepository;
    private final JpaStateRepository jpaStateRepository;
    private final JpaCategoryRepository jpaCategoryRepository;

    public void save(Accommodation accommodation) {
        jpaStateRepository.save(accommodation.getCity().getState());
        jpaCategoryRepository.save(accommodation.getCity().getCategory());
        jpaCityRepository.save(accommodation.getCity());
    }
}
