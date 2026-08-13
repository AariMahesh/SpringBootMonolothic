package com.myproject.mono.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer StockQty;
    private String category;
    private String imageUrl;
}
