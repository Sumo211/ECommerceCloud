package com.datn.ecm.service.category;

import com.datn.ecm.model.Category;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category saveCategory(Category category) throws SQLIntegrityConstraintViolationException;

    void deleteCategory(Category category);

    Optional<Category> findCategoryById(long id);

    Optional<Category> findCategoryByName(String name);

    List<Category> findAllCategories();

}
