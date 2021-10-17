package com.project.jagoga.category.infrastructure;

import com.project.jagoga.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<Category, Long> {
}
