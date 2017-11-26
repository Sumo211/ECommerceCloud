package com.datn.ecm.service.clothes;

import com.datn.ecm.model.Clothes;
import com.datn.ecm.model.SearchCriteria;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface ClothesService {

    Clothes saveClothes(Clothes product) throws SQLIntegrityConstraintViolationException;

    void deleteClothes(Clothes product);

    Optional<Clothes> findClothesById(long id);

    List<Clothes> findAllClothes();

    List<Clothes> getTopSaleOffClothes(int size);

    List<Clothes> getTopNewClothes(int size);

    List<Clothes> getTopBestSellerClothes(int size);

    List<Clothes> getClothesInShoppingCart(String clothesIds);

    List<Clothes> findClothesByName(String name);

    List<Clothes> getAllClothesOfCategory(long categoryId);

    List<Clothes> findByCriteria(SearchCriteria searchCriteria);

}
