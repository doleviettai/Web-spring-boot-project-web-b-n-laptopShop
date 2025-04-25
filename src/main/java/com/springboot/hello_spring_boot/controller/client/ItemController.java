package com.springboot.hello_spring_boot.controller.client;

import com.springboot.hello_spring_boot.domain.Cart;
import com.springboot.hello_spring_boot.domain.CartDetail;
import com.springboot.hello_spring_boot.domain.Product;
import com.springboot.hello_spring_boot.domain.User;
import com.springboot.hello_spring_boot.domain.dto.ProductCriteriaDTO;
import com.springboot.hello_spring_boot.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProductPage (Model model ,
                                  ProductCriteriaDTO productCriteriaDTO,
                                  HttpServletRequest request
                                  ){
        int page = 1;
        try{if(productCriteriaDTO.getPage().isPresent()){
            page = Integer.parseInt(productCriteriaDTO.getPage().get());
        }
        }catch (Exception e){

        }

        //check sort price
//        Pageable pageable = null;
        Pageable pageable = PageRequest.of(page - 1 , 30);
        if(productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()){
            String sort = productCriteriaDTO.getSort().get();
            if(sort.equals("gia-tang-dan")){
                pageable = PageRequest.of(page - 1 ,  30 , Sort.by("price").ascending());
            } else if (sort.equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1 , 30, Sort.by("price").descending());
            }
        }



        Page<Product> prs = this.productService.fetchProductsWithSpec(pageable , productCriteriaDTO);


        List<Product> products = prs.getContent().size() > 0 ? prs.getContent() : new ArrayList<Product>();

        String qs = request.getQueryString();
        if (qs!=null && qs.isBlank()){
            qs = qs.replace("page="+page,"");
        }

        model.addAttribute("products" , products);
        model.addAttribute("currentPage" , page);
        model.addAttribute("totalPages" , prs.getTotalPages());
        model.addAttribute("queryString" , qs);


        return "client/product/show";
    }

    @GetMapping("/product/{id}")
    public String getProductPage( Model model , @PathVariable long id){
        Product pro = this.productService.fetchProductById(id).get();
        model.addAttribute("id" , id);
        model.addAttribute("product" , pro);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id , HttpServletRequest request){
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email , productId , session , 1);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("cart", cart);

        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable long id , HttpServletRequest request){
        HttpSession session = request.getSession(false);
        long cartDetailId = id;
        this.productService.handleRemoveCarDetail(cartDetailId , session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThankYouPage(Model model) {

        return "client/cart/thanks";
    }

    @PostMapping("/add-product-from-view-detail")
    public String handleAddProductFromViewDetail(
            @RequestParam("id") long id,
            @RequestParam("quantity") long quantity,
            HttpServletRequest request
    ){
       HttpSession session = request.getSession(false);
       String email = (String) session.getAttribute("email");
       this.productService.handleAddProductToCart(email , id , session , quantity);
       return "redirect:/product/" + id;
    }
}
