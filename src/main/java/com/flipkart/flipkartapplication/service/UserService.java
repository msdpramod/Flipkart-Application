package com.flipkart.flipkartapplication.service;

import com.flipkart.flipkartapplication.DTOs.UserRequestDto;
import com.flipkart.flipkartapplication.DTOs.UserResponseDto;
import com.flipkart.flipkartapplication.models.User;
import com.flipkart.flipkartapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // GET all users
    public List<UserResponseDto> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserResponseDto> responseList = new ArrayList<>();

        for (User user : users) {
            responseList.add(convertToResponseDto(user));
        }

        return responseList;
    }

    // CREATE user
    public UserResponseDto createUser(UserRequestDto requestDto) {

        User user = convertToEntity(requestDto);

        User savedUser = userRepository.save(user);

        return convertToResponseDto(savedUser);
    }

    // FIND user by id
    public Optional<UserResponseDto> findUserById(UUID id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return Optional.empty();
        }

        UserResponseDto responseDto = convertToResponseDto(user.get());

        return Optional.of(responseDto);
    }

    // UPDATE user
    public Optional<UserResponseDto> updateUser(UUID id, UserRequestDto requestDto) {

        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            return Optional.empty();
        }

        User user = existingUser.get();

        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPhone(requestDto.getPhone());

        User savedUser = userRepository.save(user);

        UserResponseDto responseDto = convertToResponseDto(savedUser);

        return Optional.of(responseDto);
    }

    // DELETE user
    public boolean deleteUser(UUID id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return false;
        }

        userRepository.deleteById(id);

        return true;
    }

    // Convert RequestDto → Entity
    private User convertToEntity(UserRequestDto userRequestDto) {

        User user = new User();

        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setPhone(userRequestDto.getPhone());

        return user;
    }

    // Convert Entity → ResponseDto
    private UserResponseDto convertToResponseDto(User user) {

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());

        return userResponseDto;
    }
}