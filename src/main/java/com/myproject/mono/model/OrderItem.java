package com.myproject.mono.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
}
