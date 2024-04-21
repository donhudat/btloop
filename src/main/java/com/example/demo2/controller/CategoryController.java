package com.example.demo2.controller;

import com.example.demo2.dto.CategoryDTO;
import com.example.demo2.model.Category;
import com.example.demo2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Category>> getAllCategory() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Long>> createCategorys(@RequestBody List<CategoryDTO> listCategorys) {
        List<Long> result = new ArrayList<>();
        for (CategoryDTO category: listCategorys) {
            result.add(categoryService.create(category));
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryDTO categoryReq, @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.update(id, categoryReq));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }
}