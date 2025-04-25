package com.springboot.hello_spring_boot.repository;


import com.springboot.hello_spring_boot.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.hello_spring_boot.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
