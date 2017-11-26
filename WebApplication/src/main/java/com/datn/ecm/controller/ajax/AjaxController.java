package com.datn.ecm.controller.ajax;

import com.datn.ecm.constant.Color;
import com.datn.ecm.constant.Material;
import com.datn.ecm.constant.Size;
import com.datn.ecm.model.product.Category;
import com.datn.ecm.service.customer.CustomerService;
import com.datn.ecm.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AjaxAdminController")
@RequestMapping("/admin/ajax")
public class AjaxController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add-new-category")
    public String addNewCategory(@ModelAttribute Category category) {
        return productService.createCategory(category);
    }

    @PostMapping("/edit-category")
    public String editCategory(@ModelAttribute Category category) {
        return productService.updateCategory(category);
    }

    @PostMapping("/delete-category")
    public void deleteCategory(@RequestParam("id") long id) {
        productService.deleteCategory(id);
    }

    @PostMapping("/delete-customer")
    public void deleteCustomer(@RequestParam("id") String id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/get-all-category")
    public List<Category> getAllCategory(){
        return productService.findAllCategories();
    }

    @PostMapping("/add-new-product")
    public String addNewProduct(@RequestParam("name") String name, @RequestParam("description") String description,
                                @RequestParam("image") String image, @RequestParam("price") double price,
                                @RequestParam("manufacture") String manufacture, @RequestParam("size") Size size,
                                @RequestParam("color") Color color, @RequestParam("material") Material material,
                                @RequestParam("discount") double discount, @RequestParam("categoryId") String categoryName) {
        /*Clothes cloth = new Clothes();
        cloth.setName(name); cloth.setDescription(description); cloth.setImage(image); cloth.setPrice(price); cloth.setManufacture(manufacture);
        cloth.setSize(size); cloth.setColor(color); cloth.setMaterial(material); cloth.setDiscount(discount);*/

        System.out.println(name + " " + description +" "+image + " "+price+ " " +manufacture+ " "+ size + " " + color+" " + material+ " "+ discount+ " " + categoryName);
     /*   return productService.createClothes(categoryName,cloth);*/
        return "text";

    }


}
