package com.datn.ecm.controller;

import com.datn.ecm.controller.exception.ClothesNotFoundException;
import com.datn.ecm.model.Clothes;
import com.datn.ecm.model.SearchCriteria;
import com.datn.ecm.service.clothes.ClothesService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("v1/clothes")
public class ClothesController {

    private ClothesService clothesService;

    public ClothesController(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Clothes> findClothesById(@PathVariable long id) {
        Clothes clothes = clothesService.findClothesById(id).orElseThrow(ClothesNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK).body(clothes);
    }

    @GetMapping()
    public ResponseEntity<List<Clothes>> findAllClothes() {
        List<Clothes> clothes = clothesService.findAllClothes();

        if (clothes.isEmpty()) {
            throw new ClothesNotFoundException();
        }

        return new ResponseEntity<>(clothes, HttpStatus.OK);
    }

    @GetMapping(value = "/sale-off/{size}")
    public ResponseEntity<List<Clothes>> getTopSaleOffClothes(@PathVariable int size) {
        List<Clothes> clothes = clothesService.getTopSaleOffClothes(size);

        if (clothes.isEmpty()) {
            throw new ClothesNotFoundException();
        }

        return new ResponseEntity<>(clothes, HttpStatus.OK);
    }

    @GetMapping(value = "/new/{size}")
    public ResponseEntity<List<Clothes>> getTopNewClothes(@PathVariable int size) {
        List<Clothes> clothes = clothesService.getTopNewClothes(size);

        if (clothes.isEmpty()) {
            throw new ClothesNotFoundException();
        }

        return new ResponseEntity<>(clothes, HttpStatus.OK);
    }

    @GetMapping(value = "/best-seller/{size}")
    public ResponseEntity<List<Clothes>> getTopBestSellerClothes(@PathVariable int size) {
        List<Clothes> clothes = clothesService.getTopBestSellerClothes(size);

        if (clothes.isEmpty()) {
            throw new ClothesNotFoundException();
        }

        return new ResponseEntity<>(clothes, HttpStatus.OK);
    }

    @GetMapping(value = "/get-in-cart")
    public ResponseEntity<List<Clothes>> getClothesInShoppingCart(String clothesIds) {
        List<Clothes> clothes = clothesService.getClothesInShoppingCart(clothesIds);

        if (clothes.isEmpty()) {
            throw new ClothesNotFoundException();
        }

        return new ResponseEntity<>(clothes, HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-category/{categoryId}")
    public ResponseEntity<List<Clothes>> getAllClothesOfCategory(@PathVariable long categoryId) {
        List<Clothes> clothes = clothesService.getAllClothesOfCategory(categoryId);

        if (clothes.isEmpty()) {
            throw new ClothesNotFoundException();
        }

        return new ResponseEntity<>(clothes, HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-name/{name}")
    public ResponseEntity<List<Clothes>> findClothesByName(@PathVariable String name) {
        List<Clothes> clothes = clothesService.findClothesByName(name);

        if (clothes.isEmpty()) {
            throw new ClothesNotFoundException();
        }

        return new ResponseEntity<>(clothes, HttpStatus.OK);
    }

    @PostMapping(value = "/advanced-search")
    public ResponseEntity findByCriteria(@RequestBody SearchCriteria searchCriteria) {
        List<Clothes> clothes = clothesService.findByCriteria(searchCriteria);

        if (clothes.isEmpty()) {
            throw new ClothesNotFoundException();
        }

        return new ResponseEntity<>(clothes, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity createClothes(@RequestBody Clothes clothes) {
        try {
            clothes.setSku(generateSKU(clothes));
            clothesService.saveClothes(clothes);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new RuntimeException("SKU must be unique");
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateClothes(@PathVariable long id, @RequestBody Clothes clothes) {
        Clothes currentClothes = clothesService.findClothesById(id).orElseThrow(ClothesNotFoundException::new);
        BeanUtils.copyProperties(clothes, currentClothes, "id");
        try {
            clothesService.saveClothes(currentClothes);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            throw new RuntimeException("SKU must be unique");
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteClothes(@PathVariable long id) {
        Clothes clothes = clothesService.findClothesById(id).orElseThrow(ClothesNotFoundException::new);
        clothesService.deleteClothes(clothes);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private String generateSKU(Clothes clothes) {
        String clothesSKU;
        String [] categoryNameTokens = removeAccent(clothes.getName()).split(" ");

        clothesSKU = String.valueOf(categoryNameTokens[0].charAt(0)).toUpperCase()
                   + String.valueOf(categoryNameTokens[1].charAt(0)).toUpperCase();

        clothesSKU += addZeroNumber(clothes.getCategoryId());
        clothesSKU += "-" + System.currentTimeMillis();
        clothesSKU += "-" + String.valueOf(clothes.getSize().name().charAt(0)).toUpperCase()
                   + String.valueOf(clothes.getColor().name().charAt(0)).toUpperCase();
        return clothesSKU;
    }

    private String addZeroNumber(long number) {
        String result = "";
        if(number < 10) {
            result = "00" + number;
        } else if(number < 100) {
            result = "0" + number;
        }
        return result;
    }

    private String removeAccent(String name) {
        String temp = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

}
