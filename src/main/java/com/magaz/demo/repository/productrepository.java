package com.magaz.demo.repository;

import com.magaz.demo.entity.Category;
import com.magaz.demo.entity.products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface productrepository extends JpaRepository<products, Long> {
    List<products> findByCategory(Category category);
}