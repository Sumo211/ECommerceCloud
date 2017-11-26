package com.datn.ecm.service.product;

import com.datn.ecm.dto.SearchCriteria;
import com.datn.ecm.model.product.Category;
import com.datn.ecm.model.product.Clothes;
import com.datn.ecm.model.product.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private static final String PRODUCT_SERVICE_URL = "http://product-service";

    private static final String FAIL = "fail";

    private static final String CREATED = "created";

    private static final String UPDATED = "updated";

    private RestTemplate restTemplate;

    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //category action

    @Override
    public String createCategory(Category newCategory) {
        String result = CREATED;
        try {
            restTemplate.postForEntity(PRODUCT_SERVICE_URL + "/v1/categories", newCategory, Category.class);
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
            result = FAIL;
        }
        return result;
    }

    @Override
    public String updateCategory(Category updatedCategory) {
        String result = UPDATED;
        try {
            restTemplate.put(PRODUCT_SERVICE_URL + "/v1/categories/{id}", updatedCategory, updatedCategory.getId());
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
            result = FAIL;
        }
        return result;
    }

    @Override
    public void deleteCategory(long id) {
        try {
            restTemplate.delete(PRODUCT_SERVICE_URL + "/v1/categories/{id}", id);
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
    }

    @Override
    public Category findCategoryById(long id) {
        Category category = new Category();
        try {
            ResponseEntity<Category> entity = restTemplate.getForEntity(PRODUCT_SERVICE_URL + "/v1/categories/{id}", Category.class, id);
            category = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return category;
    }

    @Override
    public List<Category> findAllCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            ResponseEntity<List<Category>> entity = restTemplate.exchange(PRODUCT_SERVICE_URL + "/v1/categories",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Category>>() {
                    });
            categories = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return categories;
    }

    //clothes action

    @Override
    public String createClothes(String nameOfCategory, Clothes newClothes) {
        String result = CREATED;
        try {
            Category category = findCategoryByName(nameOfCategory);
            newClothes.setCategoryId(category.getId());
            restTemplate.postForEntity(PRODUCT_SERVICE_URL + "/v1/clothes", newClothes, Clothes.class);
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
            result = FAIL;
        }
        return result;
    }

    @Override
    public String updateClothes(String nameOfCategory, Clothes updatedClothes) {
        String result = UPDATED;
        try {
            Category category = findCategoryByName(nameOfCategory);
            updatedClothes.setCategoryId(category.getId());
            restTemplate.put(PRODUCT_SERVICE_URL + "/v1/clothes/{id}", updatedClothes, updatedClothes.getId());
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
            result = FAIL;
        }
        return result;
    }

    @Override
    public void deleteClothes(long id) {
        try {
            restTemplate.delete(PRODUCT_SERVICE_URL + "/v1/clothes/{id}", id);
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
    }

    @Override
    public Clothes findClothesById(long id) {
        Clothes clothes = new Clothes();
        try {
            ResponseEntity<Clothes> entity = restTemplate.getForEntity(PRODUCT_SERVICE_URL + "/v1/clothes/{id}", Clothes.class, id);
            clothes = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return clothes;
    }

    @Override
    public List<Clothes> findAllClothes() {
        List<Clothes> clothes = new ArrayList<>();
        try {
            ResponseEntity<List<Clothes>> entity = restTemplate.exchange(PRODUCT_SERVICE_URL + "/v1/clothes",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Clothes>>() {
                    });
            clothes = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return clothes;
    }

    @Override
    public List<Clothes> getTopSaleOffClothes(int size) {
        List<Clothes> clothes = new ArrayList<>();
        try {
            ResponseEntity<List<Clothes>> entity = restTemplate.exchange(PRODUCT_SERVICE_URL + "/v1/clothes/sale-off/{size}",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Clothes>>() {
                    }, size);
            clothes = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return clothes;
    }

    @Override
    public List<Clothes> getTopNewClothes(int size) {
        List<Clothes> clothes = new ArrayList<>();
        try {
            ResponseEntity<List<Clothes>> entity = restTemplate.exchange(PRODUCT_SERVICE_URL + "/v1/clothes/new/{size}",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Clothes>>() {
                    }, size);
            clothes = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return clothes;
    }

    @Override
    public List<Clothes> getTopBestSellerClothes(int size) {
        List<Clothes> clothes = new ArrayList<>();
        try {
            ResponseEntity<List<Clothes>> entity = restTemplate.exchange(PRODUCT_SERVICE_URL + "/v1/clothes/best-seller/{size}",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Clothes>>() {
                    }, size);
            clothes = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return clothes;
    }

    @Override
    public List<Clothes> getAllClothesOfCategory(long categoryId) {
        List<Clothes> clothes = new ArrayList<>();
        try {
            ResponseEntity<List<Clothes>> entity = restTemplate.exchange(PRODUCT_SERVICE_URL + "/v1/clothes/find-by-category/{categoryId}",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Clothes>>() {
                    }, categoryId);
            clothes = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return clothes;
    }

    @Override
    public List<Clothes> findClothesByName(String name) {
        List<Clothes> clothes = new ArrayList<>();
        try {
            ResponseEntity<List<Clothes>> entity = restTemplate.exchange(PRODUCT_SERVICE_URL + "/v1/clothes/find-by-name/{name}",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Clothes>>() {
                    }, name);
            clothes = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return clothes;
    }

    @Override
    public List<Clothes> findClothesByCriteria(SearchCriteria searchCriteria) {
        List<Clothes> clothes = new ArrayList<>();
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity entity = new HttpEntity(searchCriteria, httpHeaders);
            ResponseEntity<List<Clothes>> entityResponse = restTemplate.exchange(PRODUCT_SERVICE_URL + "/v1/clothes/advanced-search",
                    HttpMethod.POST, entity, new ParameterizedTypeReference<List<Clothes>>() {
                    });
            clothes = entityResponse.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(ex.getResponseBodyAsString());
        }
        return clothes;
    }

    @Override
    public int getAmountOfClothes(long clothesId) {
        int quantity = 0;
        try {
            ResponseEntity<Integer> entity = restTemplate.getForEntity(PRODUCT_SERVICE_URL + "/v1/inventories/amount/{clothesId}",
                                                    Integer.class, clothesId);
            quantity = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(ex.getResponseBodyAsString());
        }
        return quantity;
    }

    @Override
    public List<Inventory> findAllInventories() {
        List<Inventory> inventories = new ArrayList<>();
        try {
            ResponseEntity<List<Inventory>> entity = restTemplate.exchange(PRODUCT_SERVICE_URL + "/v1/inventories",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Inventory>>() {
                    });
            inventories = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(ex.getResponseBodyAsString());
        }
        return inventories;
    }

    private Category findCategoryByName(String name) {
        ResponseEntity<Category> entity = restTemplate.getForEntity(PRODUCT_SERVICE_URL + "/v1/categories/find-by-name/{name}", Category.class, name);
        return entity.getBody();
    }

}
