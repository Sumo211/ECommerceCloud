package com.datn.ecm.controller;

import com.datn.ecm.constant.CartEventType;
import com.datn.ecm.model.cart.CartEvent;
import com.datn.ecm.model.cart.CartEventDTO;
import com.datn.ecm.model.customer.Customer;
import com.datn.ecm.model.customer.CustomerDTO;
import com.datn.ecm.model.order.LineItem;
import com.datn.ecm.model.order.OrderDTO;
import com.datn.ecm.security.CustomUserDetails;
import com.datn.ecm.service.cart.CartService;
import com.datn.ecm.service.order.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShoppingController {

    private HttpSession session;

    private CartService cartService;

    private OrderService orderService;

    public ShoppingController(HttpSession session, CartService cartService, OrderService orderService) {
        this.session = session;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    //add cart event
    @PostMapping(value = "/add-cart-event")
    @ResponseBody
    public String addCartEvent(@RequestBody CartEventDTO cartEventDTO) {
        CartEvent cartEvent = CartEvent.builder()
                .cartEventType(cartEventDTO.getCartEventType())
                .ownerId(getCartOwner())
                .clothesId(cartEventDTO.getClothesId())
                .quantity(cartEventDTO.getQuantity())
                .build();

        return cartService.saveEvent(cartEvent);
    }

    //clear cart
    @GetMapping(value = "/clear-cart")
    public String clearCart() {
        CartEvent cartEvent = CartEvent.builder()
                .cartEventType(CartEventType.CLEAR_CART)
                .ownerId(getCartOwner())
                .build();
        String result = cartService.saveEvent(cartEvent);

        if (result.equals("created")) {
            return "redirect:/get-cart";
        } else {
            return "home/error";
        }
    }

    //show cart
    @GetMapping(value = "/get-cart")
    public String getShoppingCart(@RequestParam(value = "status", required = false) String status, Model model) {
        List<LineItem> cartDetails = cartService.getShoppingCart(getCartOwner());
        double totalPrice = cartDetails.stream().mapToDouble(item -> (item.getClothes().getPrice() -
                item.getClothes().getPrice() * item.getClothes().getDiscount()) * item.getQuantity()).sum();

        model.addAttribute("status", status);
        model.addAttribute("lineItems", cartDetails);
        model.addAttribute("subTotal", totalPrice);
        return "home/shopping";
    }

    //show cart ajax
    @GetMapping(value = "/get-cart-ajax")
    public String getShoppingCartForAjax(Model model) {
        List<LineItem> cartDetails = cartService.getShoppingCart(getCartOwner());
        double totalPrice = cartDetails.stream().mapToDouble(item -> (item.getClothes().getPrice() -
                item.getClothes().getPrice() * item.getClothes().getDiscount()) * item.getQuantity()).sum();

        model.addAttribute("lineItems", cartDetails);
        model.addAttribute("subTotal", totalPrice);
        model.addAttribute("cartSize", cartDetails.size());
        return "home/fragment/cart";
    }

    //checkout
    @GetMapping(value = "/checkout")
    public String checkout(RedirectAttributes redirectAttributes, Model model) {
        String checkoutMessage = orderService.checkout(getCartOwner());
        if(checkoutMessage.contains("created")) {
            session.setAttribute("orderId", checkoutMessage.split(":")[1]);

            List<LineItem> cartDetails = cartService.getShoppingCart(getCartOwner());
            double totalPrice = cartDetails.stream().mapToDouble(item -> (item.getClothes().getPrice() -
                    item.getClothes().getPrice() * item.getClothes().getDiscount()) * item.getQuantity()).sum();

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof CustomUserDetails) {
                Customer customer = ((CustomUserDetails) principal).getCustomer();
                CustomerDTO customerDTO = CustomerDTO.builder()
                                            .name(customer.getName())
                                            .phone(customer.getPhone())
                                            .email(customer.getAccount().getEmail())
                                            .address(customer.getAddress().getStreet())
                                            .city(customer.getAddress().getCity())
                                            .build();
                model.addAttribute("customerDTO", customerDTO);
            } else {
                model.addAttribute("customerDTO", new CustomerDTO());
            }

            model.addAttribute("lineItems", cartDetails);
            model.addAttribute("subTotal", totalPrice);
            return "home/checkout";
        } else {
            redirectAttributes.addFlashAttribute("checkoutMessage", checkoutMessage);
            return "redirect:/get-cart";
        }
    }

    //place order
    @PostMapping(value = "/place-order")
    public String placeOrder(@ModelAttribute CustomerDTO customerDTO, Model model) {
        OrderDTO result = orderService.placeOrder((String)session.getAttribute("orderId"), getCartOwner(), customerDTO);
        if(result != null) {
            model.addAttribute("orderDTO", result);
            return "home/received";
        } else {
            return "home/error";
        }
    }

    private String getCartOwner() {
        String customerId = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            customerId = ((CustomUserDetails) principal).getCustomer().getId();
        }

        return (customerId != null) ? customerId : session.getId();
    }

}
