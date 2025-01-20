package com.magaz.demo.controller;

import com.magaz.demo.entity.Category;
import com.magaz.demo.repository.productrepository;
import com.magaz.demo.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    private final productrepository productRepository;
    private final CategoryRepository categoryRepository;

    public CategoryController(productrepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories/list";
    }

    @GetMapping("/categories/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/add";
    }

    @PostMapping("/categories/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add";
        }
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    // Показать форму редактирования категории
    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категория с ID " + id + " не найдена."));
        model.addAttribute("category", category);
        return "categories/edit";
    }

    // Обработать сохранение изменений категории
    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute("category") Category category) {
        category.setId(id);
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    // Удалить категорию
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категория с ID " + id + " не найдена."));
        categoryRepository.delete(category);
        return "redirect:/categories";
    }

    @GetMapping("/category/{categoryName}")
    public String getProductsByCategory(@PathVariable String categoryName, Model model) {
        var category = categoryRepository.findByName(categoryName);
        if (category.isPresent()) {
            model.addAttribute("products", productRepository.findByCategory(category.get()));
            model.addAttribute("title", "Товары из категории: " + categoryName);
        } else {
            model.addAttribute("products", null);
            model.addAttribute("title", "Категория не найдена");
        }
        return "products";
    }
}
