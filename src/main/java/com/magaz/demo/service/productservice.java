package com.magaz.demo.service;

import com.magaz.demo.entity.Category;
import com.magaz.demo.entity.Supplier;
import com.magaz.demo.entity.products;
import com.magaz.demo.repository.CategoryRepository;
import com.magaz.demo.repository.SupplierRepository;
import com.magaz.demo.repository.productrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class    productservice {

    @Autowired
    private productrepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public List<products> getAllProducts() {
        return productRepository.findAll();
    }

    public List<products> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public Optional<products> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public products saveProduct(products product) {
        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID"));

        Supplier supplier = supplierRepository.findById(product.getSupplier().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Supplier ID"));

        product.setCategory(category);
        product.setSupplier(supplier);

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
