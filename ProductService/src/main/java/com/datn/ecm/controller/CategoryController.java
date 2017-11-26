package com.datn.ecm.controller;

import com.datn.ecm.controller.exception.CategoryExistException;
import com.datn.ecm.controller.exception.CategoryNotFoundException;
import com.datn.ecm.model.Category;
import com.datn.ecm.service.category.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/find-by-name/{name}")
    public ResponseEntity<Category> findCategoryByName(@PathVariable String name) {
        Category category = categoryService.findCategoryByName(name).orElseThrow(CategoryNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable long id) {
        Category category = categoryService.findCategoryById(id).orElseThrow(CategoryNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping()
    public ResponseEntity<List<Category>> findAllCategories() {
        List<Category> categories = categoryService.findAllCategories();

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity createCategory(@RequestBody Category category) {
        try {
            categoryService.saveCategory(category);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new CategoryExistException(category.getName());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateCategory(@PathVariable long id, @RequestBody Category category) {
        Category currentCategory = categoryService.findCategoryById(id).orElseThrow(CategoryNotFoundException::new);
        BeanUtils.copyProperties(category, currentCategory, "id");
        try {
            categoryService.saveCategory(currentCategory);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            throw new CategoryExistException(category.getName());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCategory(@PathVariable long id) {
        Category category = categoryService.findCategoryById(id).orElseThrow(CategoryNotFoundException::new);
        categoryService.deleteCategory(category);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
