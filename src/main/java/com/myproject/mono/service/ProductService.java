package com.myproject.mono.service;

import com.myproject.mono.dto.ProductRequest;
import com.myproject.mono.dto.ProductResponse;
import com.myproject.mono.model.Product;
import com.myproject.mono.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<ProductResponse> fetAllProducts() {
        return productRepository.findAll().stream().map(this::mapToResponse).toList();
    }
    public List<ProductResponse> getAllProductsByActiveStatus() {
        productRepository.findByActiveTrue();
        return productRepository.findByActiveTrue().stream().map(this::mapToResponse).toList();
    }
    public Optional<ProductResponse> getProductById(Integer id) {
        return productRepository.findById(id).map(this::mapToResponse);
    }
    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream().map(this::mapToResponse
        ).toList();
    }
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        mapToEntity(productRequest,product);
        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);

    }
    public Optional<ProductResponse> updateProduct(Integer id, ProductRequest productRequest) {
        return productRepository.findById(id).map(product -> {
            mapToEntity(productRequest,product);
            Product savedProduct = productRepository.save(product);
            return mapToResponse(savedProduct);
        });
    }
    public boolean deleteProduct(Integer id) {
        productRepository.findById(id).map(product -> {
            product.setActive(false);
            productRepository.save(product);
            return true;
        });
        return false;
    }
    private void mapToEntity(ProductRequest productRequest, Product product)
    {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setStockQty(productRequest.getStockQty());
        product.setImageUrl(productRequest.getImageUrl());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
    }
    private ProductResponse mapToResponse(Product savedProduct)
    {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(savedProduct.getName());
        productResponse.setId(savedProduct.getId());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setStockQty(savedProduct.getStockQty());
        productResponse.setImageUrl(savedProduct.getImageUrl());
        productResponse.setCategory(savedProduct.getCategory());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setActive(savedProduct.getActive());
        return productResponse;
    }



}
