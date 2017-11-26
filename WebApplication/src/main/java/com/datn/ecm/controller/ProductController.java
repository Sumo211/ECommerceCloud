package com.datn.ecm.controller;

import com.datn.ecm.constant.Color;
import com.datn.ecm.constant.Material;
import com.datn.ecm.constant.Size;
import com.datn.ecm.dto.SearchCriteria;
import com.datn.ecm.model.product.Category;
import com.datn.ecm.model.product.Clothes;
import com.datn.ecm.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpSession session;

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        List<Resource<Category>> categories = createCategoryResourceCollection(productService.findAllCategories());

        List<Resource<Clothes>> saleOffClothes = createClothesResourceCollection(productService.getTopSaleOffClothes(3));
        List<Resource<Clothes>> newClothes = createClothesResourceCollection(productService.getTopNewClothes(4));
        List<Resource<Clothes>> bestSellerClothes = createClothesResourceCollection(productService.getTopBestSellerClothes(4));

        model.addAttribute("categories", categories);
        model.addAttribute("saleOffClothes", saleOffClothes);
        model.addAttribute("newClothes", newClothes);
        model.addAttribute("bestSellerClothes", bestSellerClothes);

        session.setAttribute("clothesName", "");
        session.setAttribute("clothesCategory", "");
        return "home/index";
    }

    @GetMapping(value = "/clothes/{id}")
    public String showDetailClothes(@PathVariable long id, Model model) {
        model.addAttribute("clothes", productService.findClothesById(id));
        model.addAttribute("inStock", productService.getAmountOfClothes(id));
        return "home/detail";
    }

    @GetMapping(value = "/search-by-name")
    public String findClothesByName(String name, Model model) {
        List<Resource<Clothes>> result = createClothesResourceCollection(productService.findClothesByName(name));
        model.addAttribute("searchClothes", result);

        model.addAttribute("sizes", Arrays.asList(Size.S.name(), Size.M.name(), Size.L.name(), Size.XL.name(), Size.XXL.name()));
        model.addAttribute("colors", Arrays.asList(Color.WHITE.name(), Color.BLACK.name(), Color.BLUE.name(), Color.RED.name(), Color.YELLOW.name()));
        model.addAttribute("materials", Arrays.asList(Material.COTTON.name(), Material.JEANS.name()));
        model.addAttribute("prices", Arrays.asList("100.000 VND", "200.000 VND", "500.000 VND", "1.000.000 VND", "2.000.000 VND", "5.000.000 VND"));

        session.setAttribute("clothesName", name);
        session.setAttribute("clothesCategory", "");
        return "home/clothes";
    }

    @GetMapping(value = "/categories/{id}")
    public String findClothesByCategory(@PathVariable long id, Model model) {
        List<Resource<Clothes>> result = createClothesResourceCollection(productService.getAllClothesOfCategory(id));
        model.addAttribute("searchClothes", result);

        model.addAttribute("sizes", Arrays.asList(Size.S.name(), Size.M.name(), Size.L.name(), Size.XL.name(), Size.XXL.name()));
        model.addAttribute("colors", Arrays.asList(Color.WHITE.name(), Color.BLACK.name(), Color.BLUE.name(), Color.RED.name(), Color.YELLOW.name()));
        model.addAttribute("materials", Arrays.asList(Material.COTTON.name(), Material.JEANS.name()));
        model.addAttribute("prices", Arrays.asList("100.000 VND", "200.000 VND", "500.000 VND", "1.000.000 VND", "2.000.000 VND", "5.000.000 VND"));

        session.setAttribute("clothesName", "");
        session.setAttribute("clothesCategory", String.valueOf(id));
        return "home/clothes";
    }

    @PostMapping(value = "/advanced-search")
    public String findClothesByCriteria(@RequestBody SearchCriteria searchCriteria, Model model) {
        searchCriteria.setName((String)session.getAttribute("clothesName"));
        searchCriteria.setCategory((String)session.getAttribute("clothesCategory"));
        List<Resource<Clothes>> result = createClothesResourceCollection(productService.findClothesByCriteria(searchCriteria));
        model.addAttribute("clothes", result);
        return "home/fragment/clothes";
    }

    private List<Resource<Clothes>> createClothesResourceCollection(List<Clothes> clothes) {
        List<Resource<Clothes>> resources = new ArrayList<>();
        clothes.forEach(item -> resources.add(createClothesResource(item)));
        return resources;
    }

    private Resource<Clothes> createClothesResource(Clothes clothes) {
        Resource<Clothes> resource = new Resource<>(clothes);
        resource.add(linkTo(ProductController.class).slash("/clothes/" + clothes.getId()).withSelfRel());
        return resource;
    }

    private List<Resource<Category>> createCategoryResourceCollection(List<Category> categories) {
        List<Resource<Category>> resources = new ArrayList<>();
        categories.forEach(item -> resources.add(createCategoryResource(item)));
        return resources;
    }

    private Resource<Category> createCategoryResource(Category category) {
        Resource<Category> resource = new Resource<>(category);
        resource.add(linkTo(ProductController.class).slash("/categories/" + category.getId()).withSelfRel());
        return resource;
    }

}
