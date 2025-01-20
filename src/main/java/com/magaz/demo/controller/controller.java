package com.magaz.demo.controller;

import com.magaz.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class controller {

    private final CategoryRepository categoryRepository;
    @Autowired
        public controller(CategoryRepository categoryRepository) {
            this.categoryRepository = categoryRepository;
        }

        @GetMapping("/greeting")
        public String home(Model model) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("title", "Добро пожаловать в магазин 'У Илюхи'");
            return "home"; // Здесь должно совпадать с именем HTML-файла в папке templates
        }
    }

