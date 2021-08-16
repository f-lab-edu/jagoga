package com.project.jagoga.accommodation.infrastructure;

import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationRepository;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryAccommodationRepository implements AccommodationRepository {

    private static ConcurrentMap<Long, Accommodation> accommodationStore = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong();

    @Override
    public Long save(Accommodation accommodation) {
        accommodation.setAccommodationId(sequence.incrementAndGet());
        accommodationStore.put(accommodation.getAccommodationId(), accommodation);
        return accommodation.getAccommodationId();
    }

    @Override
    public Long delete(Long accommodationId) {
        return accommodationStore.remove(accommodationId).getAccommodationId();
    }

    @Override
    public List<Accommodation> findAll() {
        return new ArrayList<>(accommodationStore.values());
    }

    @Override
    public Optional<Accommodation> findById(Long accommodationId) {
        return Optional.ofNullable(accommodationStore.get(accommodationId));
    }

    @Override
    public Optional<Accommodation> findByName(String accommodationName) {
        return accommodationStore.values().stream()
                .filter(accommodation -> StringUtils.equals(accommodation.getAccommodationName(), accommodationName))
                .findAny();
    }
}
