package com.myproject.mono.service;

import com.myproject.mono.dto.CartItemRequest;
import com.myproject.mono.model.CartItem;
import com.myproject.mono.model.Product;
import com.myproject.mono.model.User;
import com.myproject.mono.repository.CartItemRepository;
import com.myproject.mono.repository.ProductRepository;
import com.myproject.mono.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    public boolean addToCart(String userId, CartItemRequest request) {
        // look for product
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if(productOpt.isEmpty())
        {
            return false;
        }
        Product product = productOpt.get();
        if(product.getStockQty()<request.getQuantity())
        {
            return false;
        }
        Optional<User> userOpt = userRepository.findById(Integer.valueOf(userId));
        if(userOpt.isEmpty())
        {
            return false;
        }
        User user = userOpt.get();
        CartItem existingCartItem=cartItemRepository.findByUserAndProduct(user,product);
        if(existingCartItem!=null)
        {
            existingCartItem.setQuantity(existingCartItem.getQuantity()+request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }
        else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;



    }

    public boolean deleteItemFromCart(String userId, Integer productId) {
        Optional<Product> productOpt = productRepository.findById(productId);

        Optional<User> userOpt = userRepository.findById(Integer.valueOf(userId));
        if (productOpt.isPresent() && userOpt.isPresent())
        {
            return cartItemRepository.deleteByUserAndProduct(userOpt.get(),productOpt.get());
        }
        return false;
    }

    public List<CartItem> getCartItems(String userId) {
        return userRepository.findById(Integer.valueOf(userId)).map(cartItemRepository::findByUser)
                .orElse(List.of());
    }
}
