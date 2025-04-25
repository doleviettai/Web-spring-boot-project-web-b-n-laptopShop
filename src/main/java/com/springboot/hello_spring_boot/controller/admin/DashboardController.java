package com.springboot.hello_spring_boot.controller.admin;

import com.springboot.hello_spring_boot.service.OrderService;
import com.springboot.hello_spring_boot.service.ProductService;
import com.springboot.hello_spring_boot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public DashboardController(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/admin")
    public String getDashboard(Model model){
        model.addAttribute("countUsers" , this.userService.countUsers());
        model.addAttribute("countProducts" , this.productService.countProduct());
        model.addAttribute("countOrders" , this.orderService.countOrder());
        return "/admin/dashboard/show";
    }
}
