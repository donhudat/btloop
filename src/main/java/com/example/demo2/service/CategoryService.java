package com.example.demo2.service;

import com.example.demo2.dto.CategoryDTO;
import com.example.demo2.model.Category;
import com.example.demo2.repos.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public Category get(final Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final CategoryDTO categoryDTO) {
        final Category category = modelMapper.map(categoryDTO, Category.class);
        return categoryRepository.save(category).getId();
    }

    public Category update(Long id, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        category.setName(categoryDTO.getName());
        return categoryRepository.save(category);
    }

    public Integer delete(final Long id) {
        try {
            categoryRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}