package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;
import com.flipkart.flipkartapplication.DTOs.OrderRequestDto;
import com.flipkart.flipkartapplication.DTOs.OrderResponseDto;
import com.flipkart.flipkartapplication.models.Cart;
import com.flipkart.flipkartapplication.models.CartItem;
import com.flipkart.flipkartapplication.models.Order;
import com.flipkart.flipkartapplication.models.User;
import com.flipkart.flipkartapplication.repository.CartRepository;
import com.flipkart.flipkartapplication.repository.OrderRepository;
import com.flipkart.flipkartapplication.repository.UserRepository;
import com.flipkart.flipkartapplication.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Override
    public OrderResponseDto placeOrder(UUID userId, OrderRequestDto orderRequestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart();
        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(com.flipkart.flipkartapplication.models.OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setTotalAmount(0.0);
        order.setItems(new ArrayList<>());
        order.setUser(user);
        order.setTotalAmount(0.0);
        order.setItems(new ArrayList<>());
        order.setUser(user);

        double total = 0.0;
        List<CartItemResponseDto> orderItemsDto = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            order.getItems().add(item);

            CartItemResponseDto dto = new CartItemResponseDto();
            dto.setId(item.getId());
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setPrice(item.getProduct().getPrice());
            dto.setQuantity(item.getQuantity());
            dto.setActive(item.isActive());
            dto.setCreatedAt(item.getCreatedAt());
            dto.setUpdatedAt(item.getUpdatedAt());
            orderItemsDto.add(dto);

            total += item.getProduct().getPrice().doubleValue() * item.getQuantity();
        }

        order.setTotalAmount(total);

        // clear cart after order
        cart.getItems().clear();
        cartRepository.save(cart);

        Order savedOrder = orderRepository.save(order);

        return mapToResponseDto(savedOrder, orderItemsDto);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> response = new ArrayList<>();
        for (Order order : orders) {
            response.add(mapToResponseDto(order, null));
        }
        return response;
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(UUID userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponseDto> response = new ArrayList<>();
        for (Order order : orders) {
            response.add(mapToResponseDto(order, null));
        }
        return response;
    }

    @Override
    public OrderResponseDto getOrderById(UUID orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        return mapToResponseDto(orderOpt.get(), null);
    }

    @Override
    public OrderResponseDto updateOrderStatus(UUID orderId, String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        Order order = orderOpt.get();
        order.setStatus(com.flipkart.flipkartapplication.models.OrderStatus.valueOf(status));
        order.setUpdatedAt(LocalDateTime.now());

        Order updated = orderRepository.save(order);
        return mapToResponseDto(updated, null);
    }

    private OrderResponseDto mapToResponseDto(Order order, List<CartItemResponseDto> itemsDto) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setItems(itemsDto != null ? itemsDto : new ArrayList<>());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        return dto;
    }
}