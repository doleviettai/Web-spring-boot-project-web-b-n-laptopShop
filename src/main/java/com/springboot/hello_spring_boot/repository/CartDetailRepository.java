package com.springboot.hello_spring_boot.repository;


import com.springboot.hello_spring_boot.domain.Cart;
import com.springboot.hello_spring_boot.domain.CartDetail;
import com.springboot.hello_spring_boot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail , Long> {
    boolean existsByCartAndProduct(Cart cart , Product product);
    CartDetail findByCartAndProduct(Cart cart , Product product);

    Optional<CartDetail> findById(CartDetail cartDetail);
}
