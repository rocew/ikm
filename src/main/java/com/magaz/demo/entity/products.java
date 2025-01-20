package com.magaz.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "products")
public class products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_supplier", referencedColumnName = "id", nullable = false)
    private Supplier supplier;

    @NotBlank(message = "Название продукта не может быть пустым")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Описание продукта не может быть пустым")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Цена продукта не может быть пустой")
    @Positive(message = "Цена продукта должна быть положительной")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "Количество продукта не может быть пустым")
    @Positive(message = "Количество продукта должно быть положительным")
    @Column(nullable = false)
    private Integer quantity;


    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}