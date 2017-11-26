package com.datn.ecm.repository.category;

import com.datn.ecm.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findById(long id);

    Optional<Category> findByName(String name);

}
