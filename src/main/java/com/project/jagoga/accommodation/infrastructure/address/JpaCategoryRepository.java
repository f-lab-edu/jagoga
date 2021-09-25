package com.project.jagoga.accommodation.infrastructure.address;

import com.project.jagoga.accommodation.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<Category, Long> {
}
