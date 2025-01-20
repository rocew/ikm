package com.magaz.demo.controller;

import com.magaz.demo.entity.Customer;
import com.magaz.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers/list";
    }

    @GetMapping("/add")
    public String showAddForm(@RequestParam(value = "returnToOrder", required = false, defaultValue = "false") boolean returnToOrder, Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("returnToOrder", returnToOrder);
        return "customers/add";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute("customer") Customer customer,
                              @RequestParam(value = "returnToOrder", required = false, defaultValue = "false") boolean returnToOrder) {
        customerService.saveCustomer(customer);
        if (returnToOrder) {
            return "redirect:/orders/add";
        }
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customers/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute("customer") Customer customer) {
        customer.setId(id);
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}