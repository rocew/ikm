package com.magaz.demo.controller;

import com.magaz.demo.entity.Customer;
import com.magaz.demo.entity.Order;
import com.magaz.demo.entity.products;
import com.magaz.demo.service.CustomerService;
import com.magaz.demo.service.productservice;
import com.magaz.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private productservice productService;

    // Список заказов
    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    // Показать форму добавления
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("products", productService.getAllProducts());
        return "orders/add";
    }

    // Обработать добавление заказа
    @PostMapping("/add")
    public String addOrder(
            @RequestParam Long productId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam Integer quantity) {

        // Проверяем, существует ли клиент с таким email
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setAddress(address);
            customerService.saveCustomer(customer);
        }

        products product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Product ID: " + productId));

        // Создаем новый заказ
        Order order = new Order();
        order.setProduct(product);
        order.setCustomer(customer);
        order.setQuantity(quantity);
        order.setOrderDate(LocalDate.now());

        // Рассчитываем общую цену
        double totalPrice = product.getPrice() * quantity;
        order.setTotalPrice(totalPrice);

        // Сохраняем заказ
        orderService.saveOrder(order);

        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Order ID: " + id));

        model.addAttribute("order", order);
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("products", productService.getAllProducts());
        return "orders/edit";
    }

    // Обработать обновление заказа
    @PostMapping("/edit/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute("order") Order order, BindingResult result) {
        if (result.hasErrors()) {
            return "orders/edit";
        }

        order.setId(id);
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    // Удалить заказ
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}