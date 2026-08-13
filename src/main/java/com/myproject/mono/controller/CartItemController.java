package com.myproject.mono.controller;

import com.myproject.mono.dto.CartItemRequest;
import com.myproject.mono.model.CartItem;
import com.myproject.mono.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vi/cart")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,@RequestBody CartItemRequest request)
    {
        if(!cartService.addToCart(userId,request))
        {
            return ResponseEntity.badRequest().body("Product oos or user not found or product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId, @PathVariable Integer productId)
    {
        boolean deletd=cartService.deleteItemFromCart(userId,productId);
        return deletd? ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }
    @GetMapping("/items/{productId}")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-User-ID") String userId)
    {
        return new ResponseEntity<>(cartService.getCartItems(userId),HttpStatus.OK);
    }
}
