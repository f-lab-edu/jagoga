package com.project.jagoga.roomtype.domain;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<RoomType> findById(long roomTypeId);
}