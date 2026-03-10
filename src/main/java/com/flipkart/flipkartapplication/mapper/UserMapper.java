package com.flipkart.flipkartapplication.mapper;

import com.flipkart.flipkartapplication.DTOs.AddressDto;
import com.flipkart.flipkartapplication.DTOs.UserRequestDto;
import com.flipkart.flipkartapplication.DTOs.UserResponseDto;
import com.flipkart.flipkartapplication.models.Address;
import com.flipkart.flipkartapplication.models.User;

public class UserMapper {

    // Entity → ResponseDto
    public static UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())          // enum → String
                .address(toAddressDto(user.getAddress()))  // null-safe
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    // RequestDto → Entity
    public static User toEntity(UserRequestDto dto) {
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();

        // address is optional at registration
        if (dto.getAddress() != null) {
            user.setAddress(toAddressEntity(dto.getAddress()));
        }

        return user;
    }

    // AddressDto → Address Entity
    public static Address toAddressEntity(AddressDto dto) {
        if (dto == null) return null;
        return Address.builder()
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .zipcode(dto.getZipcode())
                .build();
    }

    // Address Entity → AddressDto
    private static AddressDto toAddressDto(Address address) {
        if (address == null) return null;
        return AddressDto.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .zipcode(address.getZipcode())
                .build();
    }
}
