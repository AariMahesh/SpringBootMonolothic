package com.myproject.mono.repository;

import com.myproject.mono.model.CartItem;
import com.myproject.mono.model.Product;
import com.myproject.mono.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    CartItem findByUserAndProduct(User user, Product product);

    boolean deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}
