package com.springboot.hello_spring_boot.repository;

import com.springboot.hello_spring_boot.domain.Order;
import com.springboot.hello_spring_boot.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    Page<Order> findAll(Pageable page);
}
