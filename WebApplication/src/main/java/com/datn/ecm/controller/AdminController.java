package com.datn.ecm.controller;

import com.datn.ecm.dto.ChartDataDTO;
import com.datn.ecm.service.customer.CustomerService;
import com.datn.ecm.service.order.OrderService;
import com.datn.ecm.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public String adminPage() {
        return "admin/admin-home";
    }

    @GetMapping(value = "/dashboard")
    public String dashboardPage() {
        return "admin/fragment/dashboard";
    }

    @GetMapping(value = "/category")
    public String categoryPage(Model model) {
        model.addAttribute("categories", productService.findAllCategories());
        return "admin/fragment/category";
    }

    @GetMapping(value = "/product")
    public String productPage(Model model) {
        model.addAttribute("clothes", productService.findAllClothes());
        return "admin/fragment/product";
    }

    @GetMapping(value = "/customer")
    public String customerPage(Model model) {
        model.addAttribute("customers", customerService.findAllCustomers());
        return "admin/fragment/customer";
    }

    @GetMapping(value = "/transaction")
    public String transactionPage(Model model) {
        model.addAttribute("orders", orderService.findAllOrders());
        return "admin/fragment/transaction";
    }

    @GetMapping(value = "/inventory")
    public String inventoryPage(Model model) {
        model.addAttribute("inventories", productService.findAllInventories());
        return "admin/fragment/inventory";
    }


    @GetMapping (value = "/chartData")
    @ResponseBody
    public ChartDataDTO getChartData(@RequestParam("type") int type){
        List<Double> incomeByMonth = orderService.getIncomeByMonth();
        ChartDataDTO result = new ChartDataDTO();
        result.setLabel(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
        if(type == 1) {
            result.setValues(incomeByMonth);
        }
        if (type == 2) {
            //result.setValues(Lists.asList(5, new Integer[]{ 9, 7, 8, 5, 20, 15, 22, 14, 16, 14, 25, 10, 10, 16, 25, 29, 40, 10, 24, 15, 21, 34, 12, 35, 10, 16, 32, 12, 20}));
        }
        return result;
    }

}
