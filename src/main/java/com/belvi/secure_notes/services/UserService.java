package com.belvi.secure_notes.services;

import com.belvi.secure_notes.dtos.UserDTO;
import com.belvi.secure_notes.models.User;

import java.util.List;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    UserDTO getUserById(Long id);

    User findByUsername(String username);
}