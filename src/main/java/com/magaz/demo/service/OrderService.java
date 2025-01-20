package com.magaz.demo.service;

import com.magaz.demo.entity.products;
import com.magaz.demo.entity.Customer;
import com.magaz.demo.entity.Order;
import com.magaz.demo.repository.productrepository;
import com.magaz.demo.repository.CustomerRepository;
import com.magaz.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private productrepository productRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Добавление нового заказа
    public Order saveOrder(Order order) {
        Customer customer = customerRepository.findById(order.getCustomer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Customer ID"));

        products product = productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Product ID"));

        order.setCustomer(customer);
        order.setProduct(product);

        double totalPrice = product.getPrice() * order.getQuantity();
        order.setTotalPrice(totalPrice);

        // Сохраняем заказ
        return orderRepository.save(order);
    }

    // Удаление заказа
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
