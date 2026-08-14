package com.myproject.mono.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class OrderItemDto {
    private Integer id;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;
}
