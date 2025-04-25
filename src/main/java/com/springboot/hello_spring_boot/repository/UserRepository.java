package com.springboot.hello_spring_boot.repository;

import com.springboot.hello_spring_boot.domain.Product;
import com.springboot.hello_spring_boot.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // insert into user value()
    User save(User hoidanit);

    void deleteById(long id);

    User findById(long id);

    List<User> findOneByEmail(String email);

    List<User> findAll();

    boolean existsByEmail(String email);

    User findByEmail(String email);

    Page<User> findAll(Pageable page);
}
