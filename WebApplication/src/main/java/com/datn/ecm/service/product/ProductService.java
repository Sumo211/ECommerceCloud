package com.datn.ecm.service.product;

import com.datn.ecm.dto.SearchCriteria;
import com.datn.ecm.model.product.Category;
import com.datn.ecm.model.product.Clothes;
import com.datn.ecm.model.product.Inventory;

import java.util.List;

public interface ProductService {

    //category

    String createCategory(Category newCategory);

    String updateCategory(Category updatedCategory);

    void deleteCategory(long id);

    Category findCategoryById(long id);

    List<Category> findAllCategories();

    //clothes

    String createClothes(String nameOfCategory, Clothes newClothes);

    String updateClothes(String nameOfCategory, Clothes updatedClothes);

    void deleteClothes(long id);

    Clothes findClothesById(long id);

    List<Clothes> findAllClothes();

    //shopping

    List<Clothes> getTopSaleOffClothes(int size);

    List<Clothes> getTopNewClothes(int size);

    List<Clothes> getTopBestSellerClothes(int size);

    List<Clothes> getAllClothesOfCategory(long categoryId);

    List<Clothes> findClothesByName(String name);

    List<Inventory> findAllInventories();

    List<Clothes> findClothesByCriteria(SearchCriteria searchCriteria);

    int getAmountOfClothes(long clothesId);

}
