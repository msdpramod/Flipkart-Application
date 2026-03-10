package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.CartItemResponseDto;
import com.flipkart.flipkartapplication.exception.ResourceNotFoundException;
import com.flipkart.flipkartapplication.mapper.CartItemMapper;
import com.flipkart.flipkartapplication.models.Cart;
import com.flipkart.flipkartapplication.models.CartItem;
import com.flipkart.flipkartapplication.models.User;
import com.flipkart.flipkartapplication.repository.CartRepository;
import com.flipkart.flipkartapplication.repository.UserRepository;
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
    @Transactional(readOnly = true)
    public List<CartItemResponseDto> getCartItems(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Cart cart = user.getCart();
        List<CartItemResponseDto> responseList = new ArrayList<>();

        if (cart != null) {
            for (CartItem item : cart.getItems()) {
                responseList.add(CartItemMapper.toResponseDto(item));
            }
        }

        return responseList;
    }

    @Override
    public boolean clearCart(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Cart cart = user.getCart();
        if (cart == null) return false;

        // copy to avoid ConcurrentModificationException while iterating
        List<CartItem> itemsCopy = new ArrayList<>(cart.getItems());
        for (CartItem item : itemsCopy) {
            cart.removeItem(item);
        }

        cartRepository.save(cart);  // persist cleared cart
        return true;
    }
}