package com.project.jagoga.category.application;

import com.project.jagoga.category.infrastructure.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final JpaCategoryRepository categoryRepository;
}
