package com.myproject.mono.controller;

import com.myproject.mono.dto.ProductRequest;
import com.myproject.mono.dto.ProductResponse;
import com.myproject.mono.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts()
    {
        return new ResponseEntity<>(productService.fetAllProducts(),HttpStatus.OK);
    }
    @GetMapping("/byActiveTrue")
    public ResponseEntity<List<ProductResponse>> getProductsByActiveTrue()
    {
        return new ResponseEntity<>(productService.getAllProductsByActiveStatus(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id)
    {
        return productService.getProductById(id).map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword)
    {
        return new ResponseEntity<>(productService.searchProducts(keyword),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest)
    {
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest),
                HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Integer id,@RequestBody ProductRequest productRequest)
    {
        return productService.updateProduct(id,productRequest).map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id)
    {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
