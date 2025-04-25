package com.springboot.hello_spring_boot.service;

import com.springboot.hello_spring_boot.domain.dto.ProductCriteriaDTO;
import com.springboot.hello_spring_boot.repository.*;
import com.springboot.hello_spring_boot.domain.*;
//import com.springboot.hello_spring_boot.domain.Product_;
import com.springboot.hello_spring_boot.service.spectification.ProductSpecs;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;


    public ProductService(ProductRepository productRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository, UserService userService, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Product createProduct(Product pro){
        return this.productRepository.save(pro);
    }

    public Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public Page<Product> fetchProductsWithSpec(Pageable page){
        return this.productRepository.findAll(page);
    }

    public Page<Product> fetchProductsWithSpec(Pageable page , ProductCriteriaDTO productCriteriaDTO){
        Specification<Product> combinedSpec = Specification.where(null); //disconjuction

        if (productCriteriaDTO.getTarget() == null && productCriteriaDTO.getFactory() == null && productCriteriaDTO.getPrice() == null){
            return this.productRepository.findAll(page);
        }

        if(productCriteriaDTO.getTarget() != null && productCriteriaDTO.getTarget().isPresent()){
            Specification currentSpecs = ProductSpecs.matchListTarget(productCriteriaDTO.getTarget().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }

        if(productCriteriaDTO.getFactory() != null && productCriteriaDTO.getFactory().isPresent()){
            Specification currentSpecs = ProductSpecs.matchListFactory(productCriteriaDTO.getFactory().get());
            combinedSpec = currentSpecs.and(currentSpecs);
        }

        if(productCriteriaDTO.getPrice() != null && productCriteriaDTO.getPrice().isPresent()){
            Specification currentSpecs = (Specification) this.buildPriceSpectification(productCriteriaDTO.getPrice().get());
            combinedSpec = currentSpecs.and(currentSpecs);
        }
        return this.productRepository.findAll(combinedSpec, page);
    }

    //case 1
//    public Page<Product> fetchProductsWithSpec(Pageable page , double minprice){
//        return this.productRepository.findAll(ProductSpecs.minPrice(minprice), page);
//    }

    //case 2
//    public Page<Product> fetchProductsWithSpec(Pageable page , double maxprice){
//        return this.productRepository.findAll(ProductSpecs.maxPrice(maxprice), page);
//    }

    // case 3
//    public Page<Product> fetchProductsWithSpec(Pageable page , String factory){
//        return this.productRepository.findAll(ProductSpecs.matchFactory(factory), page);
//    }

    // case 4
//    public Page<Product> fetchProductsWithSpec(Pageable page , List<String> factory){
//        return this.productRepository.findAll(ProductSpecs.matchListFactory(factory) , page);
//    }

    // case 5
//    public Page<Product> fetchProductsWithSpec(Pageable page , String price){
//        //
//        if(price.equals("10-toi-15-trieu")){
//            double min = 10000000;
//            double max = 15000000;
//
//            return this.productRepository.findAll(ProductSpecs.matchPrice(min , max) , page);
//        } else if (price.equals("15-toi-30-trieu")) {
//            double min = 15000000;
//            double max = 30000000;
//
//            return this.productRepository.findAll(ProductSpecs.matchPrice(min , max) , page);
//        } else {
//            return this.productRepository.findAll(page);
//        }
//    }

    // case 6
    public Specification<Product> buildPriceSpectification(List<String> price) {
        Specification<Product> combinedSpec = Specification.where(null); // disconjunction
        for (String p : price) {
            double min = 0;
            double max = 0;

            // Set the appropriate min and max based on the price range string
            switch (p) {
                case "duoi-10-trieu":
                    min = 1;
                    max = 10000000;
                    break;
                case "10-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    break;
                case "15-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    break;
                case "tren-20-trieu":
                    min = 20000000;
                    max = 200000000;
                    break;
            }

            if (min != 0 && max != 0) {
                Specification<Product> rangeSpec = ProductSpecs.matchMultiplePrice(min, max);
                combinedSpec = combinedSpec.or(rangeSpec);
            }
        }

        return combinedSpec;
    }


    public Page<Product> fetchProducts(Pageable page){
        return this.productRepository.findAll(page);
    }

    public Optional<Product> fetchProductById(long id){
        return this.productRepository.findById(id);
    }


    public void deleteProduct(long id){
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email , long productId , HttpSession session , long quantity){
         User user = this.userService.getUserByEmail(email);
         if(user != null){
             Cart cart = this.cartRepository.findByUser(user);
             if(cart == null){
                 Cart otherCart= new Cart();
                 otherCart.setUser(user);
                 otherCart.setSum(0);

                 cart = this.cartRepository.save(otherCart);
             }

             Optional<Product> product = this.productRepository.findById(productId);
             if(product.isPresent()){
                 Product realProduct = product.get();

                 CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart , realProduct);

                 if (oldDetail == null){
                     CartDetail cd = new CartDetail();
                     cd.setCart(cart);
                     cd.setProduct(realProduct);
                     cd.setPrice(realProduct.getPrice());
                     cd.setQuantity(quantity);

                     this.cartDetailRepository.save(cd);

                     //update cart
                     int s = cart.getSum() + 1;
                     cart.setSum(cart.getSum() + 1);
                     this.cartRepository.save(cart);
                     session.setAttribute("sum" , s);

                 }else{
                     oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                     this.cartDetailRepository.save(oldDetail);
                 }

             }
         }
    }

    public Cart fetchByUser(User user){
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCarDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);

        if(cartDetailOptional.isPresent()){
            CartDetail cartDetail = cartDetailOptional.get();

            Cart currentCart = cartDetail.getCart();

            this.cartDetailRepository.deleteById(cartDetailId);

            if(currentCart.getSum() > 1){
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum" , s);
                this.cartRepository.save(currentCart);
            }else {
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum" , 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user, HttpSession session , String receiverName , String receiverAddress , String receiverPhone){
        // create order

        //create orderdetail

        //step 1: get cart bu user
        Cart cart = this.cartRepository.findByUser(user);
        if(cart != null){
            List<CartDetail> cartDetails = cart.getCartDetails();

            if(cartDetails != null){
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");
//                order = this.orderRepository.save(order);
                double sum = 0;

                for(CartDetail cd : cartDetails){
                    sum += cd.getPrice();
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);

                //create orderDetail
                for(CartDetail cd: cartDetails){
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());
                    this.orderDetailRepository.save(orderDetail);
                }

                //step 2: delete cart_detail and cart
                for (CartDetail cd : cartDetails){
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                //step 3 : update session
                session.setAttribute("sum" , 0);
            }
        }


    }

    public long countProduct() {
        return this.productRepository.count();
    }
}
