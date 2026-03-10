package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.UserRequestDto;
import com.flipkart.flipkartapplication.DTOs.UserResponseDto;
import com.flipkart.flipkartapplication.exception.BadRequestException;
import com.flipkart.flipkartapplication.exception.ResourceNotFoundException;
import com.flipkart.flipkartapplication.mapper.UserMapper;
import com.flipkart.flipkartapplication.models.User;
import com.flipkart.flipkartapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // ─── GET ALL ────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto findUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserMapper.toResponseDto(user);
    }

    // ─── CREATE ─────────────────────────────────────────────────────────────────
    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {

        // Duplicate email check
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new BadRequestException("Email already in use: " + requestDto.getEmail());
        }

        // Duplicate phone check
        if (userRepository.existsByPhone(requestDto.getPhone())) {
            throw new BadRequestException("Phone number already in use: " + requestDto.getPhone());
        }

        // Map DTO → Entity (address is optional — handled inside mapper)
        User user = UserMapper.toEntity(requestDto);
        User saved = userRepository.save(user);

        return UserMapper.toResponseDto(saved);
    }

    // ─── UPDATE ─────────────────────────────────────────────────────────────────
    @Override
    public UserResponseDto updateUser(UUID id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Email uniqueness check — allow same email for the same user
        if (!user.getEmail().equals(requestDto.getEmail())
                && userRepository.existsByEmail(requestDto.getEmail())) {
            throw new BadRequestException("Email already in use: " + requestDto.getEmail());
        }

        // Phone uniqueness check — allow same phone for the same user
        if (!user.getPhone().equals(requestDto.getPhone())
                && userRepository.existsByPhone(requestDto.getPhone())) {
            throw new BadRequestException("Phone number already in use: " + requestDto.getPhone());
        }

        // Update basic fields
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPhone(requestDto.getPhone());

        // Update address — create new if not present, update fields if already exists
        if (requestDto.getAddress() != null) {
            if (user.getAddress() == null) {
                user.setAddress(UserMapper.toAddressEntity(requestDto.getAddress()));
            } else {
                // update in-place to avoid orphan address records
                user.getAddress().setStreet(requestDto.getAddress().getStreet());
                user.getAddress().setCity(requestDto.getAddress().getCity());
                user.getAddress().setState(requestDto.getAddress().getState());
                user.getAddress().setCountry(requestDto.getAddress().getCountry());
                user.getAddress().setZipcode(requestDto.getAddress().getZipcode());
            }
        }

        User updated = userRepository.save(user);
        return UserMapper.toResponseDto(updated);
    }

    // ─── DELETE ─────────────────────────────────────────────────────────────────
    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}