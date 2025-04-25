package com.springboot.hello_spring_boot.service;

import com.springboot.hello_spring_boot.domain.Order;
import com.springboot.hello_spring_boot.domain.User;
import com.springboot.hello_spring_boot.domain.OrderDetail;
import com.springboot.hello_spring_boot.repository.OrderDetailRepository;
import com.springboot.hello_spring_boot.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository repository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = repository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> fetchAllOrders(){
        return this.orderRepository.findAll();
    }

    public Optional<Order> fetchOrderById(long id){
        return this.orderRepository.findById(id);
    }

    public Page<Order> fetchOrders(Pageable pageable){
        return this.orderRepository.findAll(pageable);
    }

    public void deleteOrderById(long id) {
        // delete order detail
        Optional<Order> orderOptional = this.fetchOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }

        this.orderRepository.deleteById(id);
    }

    public void updateOrder(Order order) {
        Optional<Order> orderOptional = this.fetchOrderById(order.getId());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }

    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }

    public long countOrder() {
        return this.orderRepository.count();
    }
}
