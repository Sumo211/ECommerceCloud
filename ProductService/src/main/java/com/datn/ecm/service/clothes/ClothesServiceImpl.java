package com.datn.ecm.service.clothes;

import com.datn.ecm.constant.Color;
import com.datn.ecm.constant.Material;
import com.datn.ecm.constant.Size;
import com.datn.ecm.model.Clothes;
import com.datn.ecm.model.SearchCriteria;
import com.datn.ecm.repository.clothes.ClothesRepository;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.datn.ecm.jooq.model.tables.Clothes.CLOTHES;

@Service
@Transactional
public class ClothesServiceImpl implements ClothesService {

    private ClothesRepository clothesRepository;

    private DSLContext dslContext;

    public ClothesServiceImpl(ClothesRepository clothesRepository, DSLContext dslContext) {
        this.clothesRepository = clothesRepository;
        this.dslContext = dslContext;
    }

    @Override
    public Clothes saveClothes(Clothes product) throws SQLIntegrityConstraintViolationException {
        return clothesRepository.save(product);
    }

    @Override
    public void deleteClothes(Clothes product) {
        clothesRepository.delete(product);
    }

    @Override
    public Optional<Clothes> findClothesById(long id) {
        return clothesRepository.findById(id);
    }

    @Override
    public List<Clothes> findAllClothes() {
        List<Clothes> clothes = new ArrayList<>();
        clothesRepository.findAll().forEach(clothes::add);
        return clothes;
    }

    @Override
    public List<Clothes> getTopSaleOffClothes(int size) {
        return clothesRepository.getTopSaleOffClothes(size);
    }

    @Override
    public List<Clothes> getTopNewClothes(int size) {
        return clothesRepository.getTopNewClothes(size);
    }

    @Override
    public List<Clothes> getTopBestSellerClothes(int size) {
        //TODO get Top Best Seller
        return Arrays.asList(clothesRepository.findOne(6L), clothesRepository.findOne(7L),
                clothesRepository.findOne(8L), clothesRepository.findOne(9L));
    }

    @Override
    public List<Clothes> getClothesInShoppingCart(String clothesIds) {
        List<Clothes> clothes = new ArrayList<>();

        if (clothesIds == null || clothesIds.isEmpty()) {
            return clothes;
        }

        String[] clothesIdArray = clothesIds.split(",");
        if (clothesIdArray.length == 0) {
            return clothes;
        }

        for (String clothesId : clothesIdArray) {
            clothes.add(clothesRepository.findOne(Long.valueOf(clothesId)));
        }

        return clothes;
    }

    @Override
    public List<Clothes> findClothesByName(String name) {
        return clothesRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Clothes> getAllClothesOfCategory(long categoryId) {
        return clothesRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Clothes> findByCriteria(SearchCriteria searchCriteria) {
        List<Clothes> clothes = new ArrayList<>();

        List<Condition> conditions = new ArrayList<>();

        if(!StringUtils.isEmpty(searchCriteria.getName())) {
            conditions.add(CLOTHES.NAME.contains(searchCriteria.getName()));
        }

        if(!StringUtils.isEmpty(searchCriteria.getCategory())) {
            conditions.add(CLOTHES.CATEGORY_ID.eq(Long.valueOf(searchCriteria.getCategory())));
        }

        if (!StringUtils.isEmpty(searchCriteria.getSku())) {
            conditions.add(CLOTHES.SKU.eq(searchCriteria.getSku()));
        }

        if (!StringUtils.isEmpty(searchCriteria.getManufacture())) {
            conditions.add(CLOTHES.SKU.eq(searchCriteria.getManufacture()));
        }

        if (searchCriteria.getPriceFrom() > 0 && searchCriteria.getPriceTo() > 0) {
            conditions.add(CLOTHES.PRICE.between(searchCriteria.getPriceFrom(), searchCriteria.getPriceTo()));
        }

        if (!StringUtils.isEmpty(searchCriteria.getSize())) {
            conditions.add(CLOTHES.SIZE.eq(searchCriteria.getSize()));
        }

        if (searchCriteria.getColors() != null && !searchCriteria.getColors().isEmpty()) {
            conditions.add(CLOTHES.COLOR.in(searchCriteria.getColors()));
        }

        if (searchCriteria.getMaterials() != null && !searchCriteria.getMaterials().isEmpty()) {
            conditions.add(CLOTHES.MATERIAL.in(searchCriteria.getMaterials()));
        }

        Result<Record> records = dslContext.select().from(CLOTHES).where(conditions).fetch();
        records.forEach(record -> clothes.add(getClothesEntity(record)));

        return clothes;
    }

    private Clothes getClothesEntity(Record record) {
        Clothes clothes = Clothes.builder()
                .categoryId(record.getValue(CLOTHES.CATEGORY_ID, Long.class))
                .name(record.getValue(CLOTHES.NAME, String.class))
                .description(record.getValue(CLOTHES.DESCRIPTION, String.class))
                .sku(record.getValue(CLOTHES.SKU, String.class))
                .image(record.getValue(CLOTHES.IMAGE, String.class))
                .price(record.getValue(CLOTHES.PRICE, Long.class))
                .size(record.getValue(CLOTHES.SIZE, Size.class))
                .color(record.getValue(CLOTHES.COLOR, Color.class))
                .material(record.getValue(CLOTHES.MATERIAL, Material.class))
                .manufacture(record.getValue(CLOTHES.MANUFACTURE, String.class))
                .discount(record.getValue(CLOTHES.DISCOUNT, Double.class))
                .build();
        clothes.setId(record.getValue(CLOTHES.ID, Long.class));
        return clothes;
    }

}
