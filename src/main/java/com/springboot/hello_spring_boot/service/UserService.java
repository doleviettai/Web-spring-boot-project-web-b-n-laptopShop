package com.springboot.hello_spring_boot.service;

import com.springboot.hello_spring_boot.domain.Product;
import com.springboot.hello_spring_boot.domain.Role;
import com.springboot.hello_spring_boot.domain.User;
import com.springboot.hello_spring_boot.domain.dto.RegisterDTO;
import com.springboot.hello_spring_boot.repository.OrderRepository;
import com.springboot.hello_spring_boot.repository.ProductRepository;
import com.springboot.hello_spring_boot.repository.RoleRepository;
import com.springboot.hello_spring_boot.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public List<User> getAllUserByEmail(String email){
        return this.userRepository.findOneByEmail(email);
    }

//    public List<User> getUserByEmail(String email){
//        return this.userRepository.findByEmail(email);
//    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }
    public User handleSaveUser(User user){
        User eric = this.userRepository.save(user);
        return eric;
    }

    public void deleteUser(long id){
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name){
        return this.roleRepository.findByName(name);
    }

    public Page<User> fetchUsers(Pageable page){
        return this.userRepository.findAll(page);
    }

//    public Optional<User> fetchUserById(long id){
//        return this.userRepository.findById(id);
//    }

    public User RegisterDTOtoUser(RegisterDTO registerDTO){
        User user = new User();
        user.setFullname(registerDTO.getFirstName()+" "+registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public long countUsers() {
        return this.userRepository.count();
    }

    public long countProducts() {
        return this.productRepository.count();
    }

    public long countOrders() {
        return this.orderRepository.count();
    }
}
