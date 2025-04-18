package com.ompreetham.mapper;

import com.ompreetham.dto.UserDTO;
import com.ompreetham.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        
        return new UserDTO(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
    
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        
        // Set timestamps if creating new user
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }
        
        user.setUpdatedAt(LocalDateTime.now());
        
        return user;
    }
    
    public void updateUserFromDTO(UserDTO userDTO, User user) {
        if (userDTO == null) {
            return;
        }
        
        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }
        
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }
        
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        
        if (userDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }
        
        user.setUpdatedAt(LocalDateTime.now());
    }
} 