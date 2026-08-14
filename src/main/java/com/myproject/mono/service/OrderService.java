package com.myproject.mono.service;

import com.myproject.mono.dto.OrderItemDto;
import com.myproject.mono.dto.OrderResponse;
import com.myproject.mono.model.*;
import com.myproject.mono.repository.OrderRepository;
import com.myproject.mono.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    public Optional<OrderResponse> createOrder(String userId) {
        //validate cartItems;
        List<CartItem> cartItems=cartService.getCartItems(userId);
        if (cartItems.isEmpty())
        {
            return Optional.empty();
            
        }
        // validate user;
        Optional<User> optionalUser = userRepository.findById(Integer.valueOf(userId));
        if (optionalUser.isEmpty())
        {
            return Optional.empty();

        }
        // calculate total price;
        BigDecimal totalPrice=cartItems.stream().map(CartItem::getPrice)
                              .reduce(BigDecimal.ZERO,BigDecimal::add);
        // create order;
        Order order = new Order();
        order.setUser(optionalUser.get());
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream().map(item -> new OrderItem(null, item.getProduct(), item.getQuantity(), item.getPrice(), order)).toList();
        order.setOrderItemList(orderItems);
        Order savedOrder = orderRepository.save(order);
        //clear cart
        cartService.clearCart(userId);
        return Optional.of(mapToOrderResponse(savedOrder));
    }
    private OrderResponse mapToOrderResponse(Order savedOrder)
    {
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getOrderStatus(),
                savedOrder.getOrderItemList().stream()
                        .map(orderItem -> new OrderItemDto(
                                orderItem.getId(),
                                orderItem.getQuantity(),
                                orderItem.getProduct().getId(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                        )).toList(),
                savedOrder.getCreatedAt()

        );
    }
}
