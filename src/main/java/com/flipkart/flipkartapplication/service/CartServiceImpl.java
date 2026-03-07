package com.flipkart.flipkartapplication.service;
import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;
import com.flipkart.flipkartapplication.models.Cart;
import com.flipkart.flipkartapplication.models.CartItem;
import com.flipkart.flipkartapplication.models.User;
import com.flipkart.flipkartapplication.repository.CartRepository;
import com.flipkart.flipkartapplication.repository.UserRepository;
import com.flipkart.flipkartapplication.service.CartItemServiceImpl;
import com.flipkart.flipkartapplication.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Override
    public List<CartItemResponseDto> getCartItems(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart();
        List<CartItemResponseDto> responseList = new ArrayList<>();
        if (cart != null) {
            for (CartItem item : cart.getItems()) {
                responseList.add(CartItemServiceImpl.mapToResponseDto(item)); // delegate mapping
            }
        }
        return responseList;
    }

    @Override
    public boolean clearCart(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart();
        if (cart == null) return false;

        List<CartItem> itemsCopy = new ArrayList<>(cart.getItems());
        for (CartItem item : itemsCopy) {
            cart.removeItem(item);
        }

        return true;
    }
}