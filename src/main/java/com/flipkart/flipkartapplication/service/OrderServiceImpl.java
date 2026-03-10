package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.AddressDto;
import com.flipkart.flipkartapplication.DTOs.OrderRequestDto;
import com.flipkart.flipkartapplication.DTOs.OrderResponseDto;
import com.flipkart.flipkartapplication.exception.BadRequestException;
import com.flipkart.flipkartapplication.exception.ResourceNotFoundException;
import com.flipkart.flipkartapplication.mapper.OrderMapper;
import com.flipkart.flipkartapplication.models.*;
import com.flipkart.flipkartapplication.repository.CartRepository;
import com.flipkart.flipkartapplication.repository.OrderRepository;
import com.flipkart.flipkartapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Override
    public OrderResponseDto placeOrder(UUID userId, OrderRequestDto orderRequestDto) {

        // 1. Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // 2. Validate cart is not empty
        Cart cart = user.getCart();
        if (cart == null || cart.getItems().isEmpty()) {
            throw new BadRequestException("Cannot place order — cart is empty");
        }

        // 3. Build OrderItems as snapshots of cart items at time of purchase
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            // validate stock before placing order
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName()
                        + ". Available: " + product.getStockQuantity());
            }

            OrderItem orderItem = OrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .priceAtPurchase(product.getPrice())   // snapshot — not a live reference
                    .quantity(cartItem.getQuantity())
                    .build();

            orderItems.add(orderItem);
            total += product.getPrice().doubleValue() * cartItem.getQuantity();

            // deduct stock
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
        }

        // 4. Build shipping address snapshot
        AddressDto addressDto = orderRequestDto.getShippingAddress();
        Address shippingAddress = Address.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .country(addressDto.getCountry())
                .zipcode(addressDto.getZipcode())
                .build();

        // 5. Build and save order
        Order order = Order.builder()
                .user(user)
                .items(orderItems)
                .shippingAddress(shippingAddress)
                .totalAmount(total)
                .orderNotes(orderRequestDto.getOrderNotes())
                .status(OrderStatus.PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);

        // 6. Clear cart after successful order
        List<CartItem> itemsCopy = new ArrayList<>(cart.getItems());
        for (CartItem item : itemsCopy) {
            cart.removeItem(item);
        }
        cartRepository.save(cart);

        return OrderMapper.toResponseDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByUser(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return OrderMapper.toResponseDto(order);
    }

    @Override
    public OrderResponseDto updateOrderStatus(UUID orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Validate that the status string maps to a valid enum value
        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid order status: " + status);
        }

        // Prevent updating a cancelled or delivered order
        if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new BadRequestException("Cannot update status of a " + order.getStatus().name().toLowerCase() + " order");
        }

        order.setStatus(newStatus);
        Order updated = orderRepository.save(order);
        return OrderMapper.toResponseDto(updated);
    }
}