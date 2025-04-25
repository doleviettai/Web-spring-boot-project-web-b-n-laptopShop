package com.springboot.hello_spring_boot.repository;


import com.springboot.hello_spring_boot.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail , Long> {
}
