package com.datn.ecm.repository.clothes;

import com.datn.ecm.model.Clothes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClothesRepository extends PagingAndSortingRepository<Clothes, Long> {

    Optional<Clothes> findById(long id);

    @Query(value = "select * from clothes c order by c.discount desc limit :size", nativeQuery = true)
    List<Clothes> getTopSaleOffClothes(@Param("size") int size);

    @Query(value = "select * from clothes c order by c.created_at desc limit :size", nativeQuery = true)
    List<Clothes> getTopNewClothes(@Param("size") int size);

    List<Clothes> findByNameContainingIgnoreCase(String clothesExample);

    List<Clothes> findByCategoryId(long categoryId);

}
