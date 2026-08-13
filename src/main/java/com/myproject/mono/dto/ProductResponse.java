package com.myproject.mono.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductResponse {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer StockQty;
    private String category;
    private String imageUrl;
    private Boolean active=true;
}
